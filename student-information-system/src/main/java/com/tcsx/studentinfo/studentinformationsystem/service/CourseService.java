package com.tcsx.studentinfo.studentinformationsystem.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.Subscription;
import com.tcsx.studentinfo.studentinformationsystem.dao.AnnouncementRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.CourseRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.LectureRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.ProfessorRepository;
import com.tcsx.studentinfo.studentinformationsystem.dao.StudentRepository;
import com.tcsx.studentinfo.studentinformationsystem.exception.EntityNotFoundException;
import com.tcsx.studentinfo.studentinformationsystem.model.Announcement;
import com.tcsx.studentinfo.studentinformationsystem.model.Board;
import com.tcsx.studentinfo.studentinformationsystem.model.Course;
import com.tcsx.studentinfo.studentinformationsystem.model.Lecture;
import com.tcsx.studentinfo.studentinformationsystem.model.Material;
import com.tcsx.studentinfo.studentinformationsystem.model.Note;
import com.tcsx.studentinfo.studentinformationsystem.model.Professor;
import com.tcsx.studentinfo.studentinformationsystem.model.Roster;
import com.tcsx.studentinfo.studentinformationsystem.model.Student;

@Service
public class CourseService {
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private AnnouncementRepository announcementRepository;
	@Autowired
	private LectureRepository lectureRepository;
	@Autowired
	private ProfessorRepository professorRepository;
	@Autowired
	private AmazonSNSClient snsClient;
	
	public Iterable<Course> findAllCourses() {
		return courseRepository.findAll();
	}
	
	public Course findCourseById(String courseId) throws EntityNotFoundException{
		Optional<Course> course = courseRepository.findById(courseId);
		if (!course.isPresent()) {
			throw new EntityNotFoundException(Course.class, courseId);
		}
		return course.get();
	}
	
	public Course updateCourse(String courseId, Course course) {
		course.setId(courseId);
		return courseRepository.save(course);
	}
	
	public Professor getProfessorOfACourse(String courseId) {
		Course course = findCourseById(courseId);
		return getProfessorOfACourse(course);
	}
	
	public Professor getProfessorOfACourse(Course course) throws EntityNotFoundException{
		String professorId = course.getProfessor();
		if(professorId == null) throw new EntityNotFoundException(Professor.class, "id not exist");
		Optional<Professor> professor = professorRepository.findById(professorId);
		if(professor.isPresent()) return professor.get();
		throw new EntityNotFoundException(Professor.class, professorId);
	}
	
	public Professor setProfessorOfACourse(String courseId, Professor professor) {
		Course course = findCourseById(courseId);
		professor.addCourse(course);
		professor =  professorRepository.save(professor);
		course.setProfessor(professor.getId());
		course = courseRepository.save(course);
		return professor;
	}
	
	public Professor deleteProfessorOfACourse(String courseId) { 
		Course course = findCourseById(courseId);
		Professor professor = getProfessorOfACourse(courseId);
		professor.deleteCourseById(courseId);
		course.setProfessor(null);
		courseRepository.save(course);
		professorRepository.delete(professor);
		return professor;
	}
	
	public List<Announcement> getAnnoucementsOfACourse(String courseId) {
		Course course = findCourseById(courseId);
		List<Announcement> announcements = new LinkedList<>();
		for (String announcement : course.getAnnouncements()) {
			announcements.add(announcementRepository.findById(announcement).get());
		}
		return announcements;
	}
	
	public Announcement getAnnoucementByIdOfACourse(String courseId, String announcementId) {
		Optional<Announcement> announcement = announcementRepository.findById(announcementId);
		if(announcement.isPresent()) return announcement.get();
		throw new EntityNotFoundException(Announcement.class, announcementId);
	}
	
	
	public Announcement addAnnoucementOfACourse(String courseId, Announcement announcement) {
		Course course = findCourseById(courseId);
		announcement.setTopicArn(course.getTopicArn());
		announcement = announcementRepository.save(announcement);
		course.addAnnouncement(announcement);
		courseRepository.save(course);
		return announcement;
	}
	
	public void deleteAnnoucementOfACourse(String courseId, String announcementId) {
		Course course = findCourseById(courseId);
		course.deleteAnnouncementById(announcementId);
		courseRepository.save(course);
		announcementRepository.deleteById(announcementId);
	}

	public Announcement updateAnnoucementByIdOfACourse(String courseId, String announcementId, Announcement announcement) {
		Announcement oldAnnouncement = getAnnoucementByIdOfACourse(courseId, announcementId);
		oldAnnouncement.setTitle(announcement.getTitle());
		oldAnnouncement.setMessage(announcement.getMessage());
		return announcementRepository.save(oldAnnouncement);
	}
	
	public List<Student> getAllStudentsOfACourse(String courseId) {
		List<Student> students = new LinkedList<>();
		HashMap<String, String> studentIds = findCourseById(courseId).getStudents();
		for (String studentId : studentIds.keySet()) {
			students.add(studentRepository.findById(studentId).get());
		}
		return students;
	}

	public Student getStudentByIdOfACourse(String courseId, String studentId) {
		return getStudentById(studentId);
	}
	
	public Student getStudentById(String studentId) throws EntityNotFoundException{
		Optional<Student> student = studentRepository.findById(studentId);
		if (!student.isPresent()) {
			throw new EntityNotFoundException(Student.class, studentId);
		}
		return student.get();
	}

	public Student addStudentOfACourse(String courseId, Student student) {
		Course course = findCourseById(courseId);
		course.addStudent(student);
		snsClient.subscribe(course.getTopicArn(), "email", student.getEmail());
		student.addCourse(courseId);
		courseRepository.save(course);
		return studentRepository.save(student);
	}

	public Student deleteStudentByIdOfACourse(String courseId, String studentId) {
		Course course = findCourseById(courseId);
		course.deleteStudentById(studentId);
		Student student = getStudentById(studentId);
		student.deleteCourseById(courseId);
		courseRepository.save(course);
		String arn = "";
		List<Subscription> list = snsClient.listSubscriptionsByTopic(course.getTopicArn())
				.getSubscriptions();
		for (Subscription subscription : list) {
			if (subscription.getEndpoint().equals(student.getEmail())) {
				arn = subscription.getSubscriptionArn();
				break;
			}
		}
		if (arn.length() > 0) {
			snsClient.unsubscribe(arn);
		}
		return studentRepository.save(student);
	}
	
	public Roster getRosterOfACourse(String courseId) {
		return findCourseById(courseId).getRoster();
	}

	public Roster setRosterOfACourse(String courseId, Roster roster) {
		Course course = findCourseById(courseId);
		course.setRoster(roster);
		courseRepository.save(course);
		return roster;
	}
	
	public void deleteRosterOfACourse(String courseId) {
		Course course = findCourseById(courseId);
		course.setRoster(null);
		courseRepository.save(course);
	}
	
	public Board getBoardOfACourse(String courseId) {
		return findCourseById(courseId).getBoard();
	}

	public Board setBoardOfACourse(String courseId, Board board) {
		Course course = findCourseById(courseId);
		course.setBoard(board);
		courseRepository.save(course);
		return board;
	}
	
	public void deleteBoardOfACourse(String courseId) {
		Course course = findCourseById(courseId);
		course.setBoard(null);
		courseRepository.save(course);
	}
	
	public List<Lecture> getAllLecturesOfACourse(String courseId) {
		List<Lecture> lectures = new LinkedList<>();
		Set<String> lectureIds = findCourseById(courseId).getLectures();
		for (String lectureId : lectureIds) {
			lectures.add(lectureRepository.findById(lectureId).get());
		}
		return lectures;
	}

	public Lecture getLectureByIdOfACourse(String courseId, String lectureId) {
		Optional<Lecture> lecture = lectureRepository.findById(lectureId);
		if (!lecture.isPresent()) {
			throw new EntityNotFoundException(Lecture.class, lectureId);
		}
		return lecture.get();
	}

	public Lecture addLectureOfACourse(String courseId, Lecture lecture) {
		Course course = findCourseById(courseId);
		course.addLecture(lecture);
		courseRepository.save(course);
		return lectureRepository.save(lecture);
	}

	public void deleteLectureByIdOfACourse(String courseId, String lectureId) {
		lectureRepository.deleteById(lectureId);
	}

	public HashMap<String, Material> getMaterials(String courseId, String lectureId) {
		return getLectureByIdOfACourse(courseId, lectureId).getMaterials();
	}

	public void addMaterial(String courseId, String lectureId, Material material) {
		Lecture lecture = getLectureByIdOfACourse(courseId, lectureId);
		lecture.addMaterial(material);
		lectureRepository.save(lecture);
	}

	public Material deleteMaterial(String courseId, String lectureId, String materialId) {
		Lecture lecture = getLectureByIdOfACourse(courseId, lectureId);
		Material material = lecture.deleteMaterialById(materialId);
		lectureRepository.save(lecture);
		return material;
	}

	public Material getMaterialById(String courseId, String lectureId, String materialId) {
		return getMaterials(courseId, lectureId).get(materialId);
	}

	public HashMap<String, Note> getNotes(String courseId, String lectureId) {
		return getLectureByIdOfACourse(courseId, lectureId).getNotes();
	}

	public void addNote(String courseId, String lectureId, Note note) {
		Lecture lecture = getLectureByIdOfACourse(courseId, lectureId);
		lecture.addNote(note);
		lectureRepository.save(lecture);
	}

	public Note deleteNote(String courseId, String lectureId, String noteId) {
		Lecture lecture = getLectureByIdOfACourse(courseId, lectureId);
		Note note = lecture.deleteNoteById(noteId);
		lectureRepository.save(lecture);
		return note;
	}

	public Note getNoteById(String courseId, String lectureId, String noteId) {
		HashMap<String, Note> map = getNotes(courseId, lectureId);
		System.out.println("\n\n\n"+ map);
		Object note = map.get(noteId);
		
		System.out.println("\n\n\n"+note + " \n\n class is:   " + note.getClass());
		System.out.println(note instanceof Note);
		return (Note) note;
	}

}
