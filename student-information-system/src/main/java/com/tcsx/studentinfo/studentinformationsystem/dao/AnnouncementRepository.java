package com.tcsx.studentinfo.studentinformationsystem.dao;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import com.tcsx.studentinfo.studentinformationsystem.model.Announcement;


@EnableScan
public interface AnnouncementRepository extends CrudRepository<Announcement, String> {

}
