package com.flp.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.flp.dao.AdminDao;
import com.flp.dao.BoardDao;
import com.flp.dao.ChapterDao;
import com.flp.dao.GradeDao;
import com.flp.dao.LODao;
import com.flp.dao.ModuleTopicDao;
import com.flp.dao.QuestionDao;
import com.flp.dao.QuestionTopicDao;
import com.flp.dao.SchoolDao;
import com.flp.dao.SectionDao;
import com.flp.dao.SessionSchoolGradeDao;
import com.flp.dao.SubjectDao;
import com.flp.dao.SubjectSectionDao;
import com.flp.dao.TopicDao;
import com.flp.model.Board;
import com.flp.model.Chapter;
import com.flp.model.EntityType;
import com.flp.model.Grade;
import com.flp.model.LearnObjective;
import com.flp.model.ModuleTopic;
import com.flp.model.Question;
import com.flp.model.QuestionComments;
import com.flp.model.QuestionTopic;
import com.flp.model.School;
import com.flp.model.SchoolSession;
import com.flp.model.Section;
import com.flp.model.Session;
import com.flp.model.SessionSchoolGrade;
import com.flp.model.Subject;
import com.flp.model.SubjectSection;
import com.flp.model.Topic;
import com.flp.model.TopicGraph;
import com.flp.model.UpdateLog;
import com.flp.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService
{
	@Autowired
	private QuestionDao questionDao;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private GradeDao gradeDao;
	
	@Autowired
	private AdminDao adminDao;

	@Autowired
	private SubjectDao subjectDao;

	@Autowired
	private TopicDao topicDao;

	@Autowired
	private LODao lODao;

	
	
	@Autowired
	private BoardDao boardDao;


	@Autowired
	private SectionDao sectionDao;

	@Autowired
	private ChapterDao chapterDao;
	
	@Autowired
	private SchoolDao schoolDao;

	@Autowired
	private QuestionTopicDao questionTopicDao;
	
	@Autowired
	private ModuleTopicDao moduleTopicDao;
	
	@Autowired
	private SubjectSectionDao subjectSectionDao;
	
	@Autowired
	private SessionSchoolGradeDao sessionSchoolGradeDao;

	@Transactional
	@Override
	public List<Grade> getGrades(Long boardId, Long schoolId, Integer status)
	{
		return gradeDao.getGrades(boardId, schoolId, status);
	}

	@Override
	@Transactional
	public List<Subject> getSubjects(Long boardId, Long schoolId, String subjectName, Integer status)
	{
		return subjectDao.getSubjects(boardId, schoolId, null, status);
	}

	@Override
	@Transactional
	public List<SubjectSection> getSubjectSections(String sectionIdString, Integer status)
	{
		return subjectSectionDao.getSubjectSections(sectionIdString, status);
	}

	@Override
	@Transactional
	public List<School> getAllSchoolDetails(Boolean status)
	{
		return adminDao.getAllSchoolDetails(status);
	}

	@Override
	@Transactional
	public Boolean addSchool(School school)
	{
		return adminDao.addSchool(school);

	}

	@Override
	@Transactional
	public Boolean deactivateSchool(Long schoolId, Integer status)
	{
		return adminDao.deactivateSchool(schoolId, status);

	}

	@Override
	@Transactional
	public School getSchoolDetailsById(Long schoolId)
	{
		return adminDao.getSchoolDetailsById(schoolId);
	}

	@Override
	@Transactional
	public boolean checkSubjectAvailability(Long boardId, Long schoolId, Integer status, String subjectName)
	{
		return subjectDao.checkSubjectAvailability(boardId, schoolId, status, subjectName);
	}

	@Override
	@Transactional
	public Subject addSubject(Subject subject)
	{
		return subjectDao.saveOrUpdateEntity(subject);
	}

	@Override
	@Transactional
	public List<Long> addSubjectSectionMapping(Subject subject, List<Section> sectionList)
	{
		return subjectSectionDao.addSubjectSectionMapping(subject, sectionList);
	}

	@Override
	@Transactional
	public void updateSubjectSectionMapping(String subjectSectionIdString, Integer status)
	{
		subjectSectionDao.updateSubjectSectionMapping(subjectSectionIdString, status);
	}

	@Override
	@Transactional
	public List<Section> getSections(String sectionIdString, Long boardId, String sessionSchoolGradeIdString, Integer status, String gradeIdString, String order)
	{
		return sectionDao.getSections(sectionIdString, boardId, sessionSchoolGradeIdString, status, gradeIdString, order);
	}

	@Override
	@Transactional
	public Chapter addChapter(Chapter chapter)
	{
		return chapterDao.saveOrUpdateEntity(chapter);
	}

	@Override
	@Transactional
	public void updateChapter(String chapterIdString, Integer status)
	{
		chapterDao.updateChapter(chapterIdString, status);
	}
	
	@Override
	@Transactional
	public void updateBoard(String boardIdString, Integer status)
	{
		boardDao.updateBoard(boardIdString, status);
	}

	@Override
	@Transactional
	public Board addBoard(Board board)
	{
		return boardDao.saveOrUpdateEntity(board);
	}
	
	@Override
	@Transactional
	public Topic addTopic(Topic topic)
	{
		return topicDao.saveOrUpdateEntity(topic);
	}
	
	@Override
	@Transactional
	public LearnObjective addLO(LearnObjective lo)
	{
		return lODao.saveOrUpdateEntity(lo);
	}

	@Transactional
	@Override
	public List<Board> getBoards(Long ctlsBoardId, Integer status)
	{
		return boardDao.getBoards(ctlsBoardId, status);
	}
	@Transactional
	@Override
	public List<Board> getBoardName(Long BoardId)
	{
		return boardDao.getBoardName(BoardId);
	}
	

	@Override
	@Transactional
	public Boolean addNewSession(Session session)
	{
		return adminDao.addNewSession(session);
	}

	@Override
	@Transactional
	public List<Session> getAllSessionList()
	{
		return adminDao.getAllSessionList();
	}

	@Override
	@Transactional
	public void saveSchoolSession(SchoolSession ss)
	{
		adminDao.saveSchoolSession(ss);
	}

	@Transactional
	@Override
	public List<Chapter> getChapters(String subjectIdString, Long gradeId, Integer status)
	{
		return chapterDao.getChapters(subjectIdString, gradeId, status);
	}

	@Transactional
	@Override
	public List<Topic> getTopics(String chapterIdString, Integer status)
	{
		return topicDao.getTopics(chapterIdString, status);
	}
	
	
	@Transactional
	@Override
	public List<LearnObjective> getLos(String topicIdString, Integer status)
	{
	 
		return lODao.getLos(topicIdString, status);
	}
	
	@Transactional
	@Override
	public Long getlocount(Long topicId ,Integer status)
	{
		 
		return lODao.getlocount(topicId, status);
	}


	@Transactional
	@Override
	public boolean checkBoardAvailability(String boardName, Integer status)
	{
		return boardDao.checkBoardAvailability(boardName, status);
	}
	
	@Transactional
	@Override
	public boolean checkChapterAvailability(Long gradeId, Long subjectId, Integer status, String chapterName)
	{
		return chapterDao.checkChapterAvailability(gradeId, subjectId, status, chapterName);
	}

	@Transactional
	@Override
	public boolean checkTopicAvailability(Long chapterId, Integer status, String chapterName)
	{
		return topicDao.checkTopicAvailability(chapterId, status, chapterName);
	}
 
	@Transactional
	@Override
	public void updateTopic(String topicIdString, Integer status)
	{
		topicDao.updateTopic(topicIdString, status);
	}
	
	
	
	 @Transactional
     @Override
     public void updateQuestion(String questopicId,String QuestionIdString, Integer status)
        {
	// TODO Auto-generated method stub
   	  questionTopicDao.updateQuestionTopic(questopicId, QuestionTopic.STATUS_INACTIVE);
   	  questionDao. updateQuestion(QuestionIdString, Question.STATUS_INACTIVE);
      
        }

	  @Transactional
      @Override
      public void updateQuestionTopic(String topicIdString, Integer status)
         {
	// TODO Auto-generated method stub
    	  questionTopicDao.updateQuestionTopic(topicIdString, QuestionTopic.STATUS_INACTIVE);
    	 
    	 

       }

	@Transactional
	@Override
	public List<Long> addSubjectAndMapping(Subject subject, String sectionIdString)
	{
		//if(subject.getId() != null)
			//subject = subjectDao.saveOrUpdateEntity(subject);
		
		return subjectSectionDao.addSubjectSectionMapping((subject.getId() != null ? subject.getId() : subjectDao.saveOrUpdateEntity(subject).getId()), sectionIdString);
	}

	@Transactional
	@Override
	public boolean checkGradeAvailability(String gradeName, Long schoolId, Long boardId, Integer status)
	{
		return gradeDao.checkGradeAvailability(gradeName, schoolId, boardId, status);
	}

	@Transactional
	@Override
	public Grade addGrade(Grade grade)
	{
		return gradeDao.saveOrUpdateEntity(grade);
	}

	@Transactional
	@Override
	public void updateGrade(String gradeIdString, Integer status)
	{
		gradeDao.updateGrade(gradeIdString, status);
	}

	@Transactional
	@Override
	public List<Long> addGradeAndSection(Grade grade, String boardIdString, String sessionSchoolGradeIdString, String sectionName)
	{
		grade = gradeDao.saveOrUpdateEntity(grade);
		
		return sectionDao.addSection(grade.getId(), boardIdString, sessionSchoolGradeIdString, sectionName);
	}

	@Transactional
	@Override
	public void updateSection(String sectionIdString, Integer status)
	{
		sectionDao.updateSection(sectionIdString, status);
	}

	@Transactional
	@Override
	public List<Long> addGradeAndSectionMapping(String sectionIdString, String gradeNameString, Long boardIdTarget)
	{
		String[] gradeNames = gradeNameString.split("##");
		String[] sectionIds = sectionIdString.split(",");
		List<Long> sectionIdList = new ArrayList<Long>();
		
		
		
		
		
		int chapterOrder, topicOrder;
		
		for(int i=0;i<gradeNames.length;i++)
		{
			// Grade saved
			Grade newGrade = new Grade();
			newGrade.setBoardId(boardIdTarget);
			newGrade.setLevelId(1L);
			newGrade.setModifiedDate(new Date());
			newGrade.setName(gradeNames[i]);
			newGrade.setSchoolId(null);
			newGrade.setStatus(Grade.STATUS_ACTIVE);
			
			newGrade = gradeDao.saveEntity(newGrade);
			
			
			
			
			
			// Section saved
			Section newSection = new Section();
			newSection.setSessionSchoolGradeId(null);
			newSection.setBoardId(boardIdTarget);
			newSection.setGradeId(newGrade.getId());		// Using newly created grade id
			newSection.setModifiedDate(new Date());
			newSection.setStatus(Section.STATUS_ACTIVE);
			newSection.setName("A");
			
			newSection = sectionDao.saveEntity(newSection);
			sectionIdList.add(newSection.getId());	
			
			
			
			//syningUpdate(sectionIdSource, boardIdTarget, gradeIdTarget, sectionIdTarget);
			
			syningUpdate(Long.valueOf(sectionIds[i]), boardIdTarget, newGrade.getId(), newSection.getId());
			
			
			
			// Adding id in sectionIdList
					
			// Getting SubjectSection mapping
			List<SubjectSection> subjectSectionList = subjectSectionDao.getSubjectSections(sectionIds[i], SubjectSection.STATUS_ACTIVE);
			
			for(SubjectSection ss : subjectSectionList)
			{
				// Saving subject
				Subject newSubject = new Subject();
				newSubject.setBoardId(boardIdTarget);
				newSubject.setRefSubjectId(ss.getSubject().getId());
				
				newSubject.setSchoolId(null);
				newSubject.setModifiedDate(new Date());
				newSubject.setName(ss.getSubject().getName());
				newSubject.setStatus(Subject.STATUS_ACTIVE);
				if(ss.getSubject().getSubImage()!=null){
					newSubject.setSubImage(ss.getSubject().getSubImage());
				}
				
				newSubject = subjectDao.saveEntity(newSubject);
				
				// Saving subjectSection mapping
				SubjectSection newSubjectSection = new SubjectSection();
				
				newSubjectSection.setRefSubjectSectionId(ss.getId());
				newSubjectSection.setModifiedDate(new Date());
				newSubjectSection.setSectionId(newSection.getId());			// Using newly created section id
				newSubjectSection.setSubjectId(newSubject.getId());			// Using newly created subject id
				newSubjectSection.setStatus(SubjectSection.STATUS_ACTIVE);
				
				newSubjectSection = subjectSectionDao.saveEntity(newSubjectSection);
				
				// Getting Chapter List
				List<Chapter> chapterList =  chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);		
				chapterOrder=1;
				for(Chapter c : chapterList)
				{
					// Saving chapter
					Chapter newChapter = new Chapter();
					//newChapter.setNameUTF8(c.getNameUTF8());
					newChapter.setRefChapterId(c.getId());
					
					newChapter.setName(c.getName());
					newChapter.setGradeId(newGrade.getId()); 						// Using newly created grade id
					newChapter.setSubjectId(newSubject.getId()); 					// Using newly created subject id
					newChapter.setBoardId(boardIdTarget);
					newChapter.setSchoolId(null);
					newChapter.setSerialOrder(chapterOrder++);
					newChapter.setModifiedDate(new Date());
					newChapter.setStatus(Chapter.STATUS_ACTIVE);
					
					newChapter = chapterDao.saveEntity(newChapter);
					
					// Getting Topic List
					List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
					 topicOrder=1;
					for(Topic t : topicList)
					{
						// Saving topic
						Topic newTopic = new Topic();
						//newTopic.setNameUTF8(t.getNameUTF8());
						
						newTopic.setRefTopicId(t.getId());
						newTopic.setName(t.getName());
						newTopic.setChapterId(newChapter.getId());						// Using newly created chapter id
						newTopic.setGradeId(newGrade.getId());							// Using newly created grade id
						newTopic.setSubjectId(newSubject.getId()); 						// Using newly created subject id
						newTopic.setBoardId(boardIdTarget);		
						newTopic.setSchoolId(null);
						newTopic.setModifiedDate(new Date());
						newTopic.setSerialOrder(topicOrder++);
						newTopic.setStatus(Topic.STATUS_ACTIVE);
						
						newTopic = topicDao.saveEntity(newTopic);
						
						// Getting questionTopic List
						List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), QuestionTopic.STATUS_ACTIVE);
						
						
						List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
						
						for(LearnObjective lolistdata : lolist)
						{
							
							
							
							
							// Saving QuestionTopic
							LearnObjective lol = new LearnObjective();
							lol.setRefLearnObjective(lolistdata.getId());
							lol.setBoardId(lolistdata.getBoardId());
							lol.setChapterId(lolistdata.getChapterId());
							lol.setGradeId(lolistdata.getGradeId());
							lol.setName(lolistdata.getName());
							lol.setStatus(lolistdata.getStatus());
							lol.setSubjectId(lolistdata.getSubjectId());
							lol.setTopicId(newTopic.getId());
							lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
							lODao.saveEntity(lol);
							/*newQuestionTopic.setLoId(qt.getLoId());
							newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());*/
							System.out.println("t.getId()old="+t.getId()+"=newTopic.getId()=="+newTopic.getId()+"==lolid=="+lol.getId());
						}
						 
						
						
						for(QuestionTopic qt : questionTopicList)
						{
							// Saving QuestionTopic
							QuestionTopic newQuestionTopic = new QuestionTopic();
							
							newQuestionTopic.setRefQuestiontopicId(qt.getId());
							newQuestionTopic.setModifiedDate(new Date());
							newQuestionTopic.setQuestionId(qt.getQuestionId());
							newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
							newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
							newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
							newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
							newQuestionTopic.setLoId(qt.getLoId());
							newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
							
							questionTopicDao.saveEntity(newQuestionTopic);
						}
						
						// Getting moduleTopic List
						List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, t.getId(), ModuleTopic.STATUS_ACTIVE);
						
						for(ModuleTopic mt : moduleTopicList)
						{
							// Saving ModuleTopic
							ModuleTopic newModuleTopic = new ModuleTopic();
							newModuleTopic.setRefModuletopicId(mt.getId());
							newModuleTopic.setModifiedDate(new Date());
							newModuleTopic.setModuleId(mt.getModuleId());
							newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
							newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
							
							moduleTopicDao.saveEntity(newModuleTopic);
						}
					}
				}
			}
			
		}
		
		return sectionIdList;
	}

	@Transactional
	@Override
	public List<Long> addSubjectAndSubjectSectionMapping(Long gradeIdSource, String subjectIdString, String subjectNameString, Long boardIdTarget, Long sectionIdTarget, Long gradeIdTarget)
	{
		String[] subjectNames = subjectNameString.split("###");
		String[] subjectIds = subjectIdString.split(",");
		List<Long> subjectSectionIdList = new ArrayList<Long>();
		List<Long> newSubjectIds=new ArrayList<Long>();
		for(String s:subjectIds){
			newSubjectIds.add(Long.parseLong(s));
		}
		Map<Long, String> subImageMap=subjectSectionDao.getSubjectImgBySubjectIds(newSubjectIds);
		int chapterOrder,topicOrder;
		for(int i=0;i<subjectIds.length;i++)
		{
			// Adding subject
			Subject newSubject = new Subject();
			newSubject.setBoardId(boardIdTarget);
			newSubject.setSchoolId(null);
			newSubject.setModifiedDate(new Date());
			newSubject.setName(subjectNames[i]);
			newSubject.setStatus(Subject.STATUS_ACTIVE);
			if(subImageMap!=null){
				String subImg=subImageMap.get(Long.parseLong(subjectIds[i]));
				if(subImg!=null){
					newSubject.setSubImage(subImg);
				}
			}
			
			
			newSubject = subjectDao.saveEntity(newSubject);
			
			// Saving subjectSection mapping
			SubjectSection newSubjectSection = new SubjectSection();
			newSubjectSection.setModifiedDate(new Date());
			newSubjectSection.setSectionId(sectionIdTarget);			
			newSubjectSection.setSubjectId(newSubject.getId());			// Using newly created subject id
			newSubjectSection.setStatus(SubjectSection.STATUS_ACTIVE);
			
			newSubjectSection = subjectSectionDao.saveEntity(newSubjectSection);
			
			subjectSectionIdList.add(newSubjectSection.getId());		// Adding id in subjectSectionIdList
			
			// Getting Chapter List
			List<Chapter> chapterList = chapterDao.getChapters(String.valueOf(subjectIds[i]), gradeIdSource, Chapter.STATUS_ACTIVE);
			chapterOrder=1;
			for(Chapter c : chapterList)
			{
				// Saving chapter
				Chapter newChapter = new Chapter();
				//newChapter.setNameUTF8(c.getNameUTF8());
				newChapter.setName(c.getName());
				newChapter.setGradeId(gradeIdTarget); 						
				newChapter.setBoardId(boardIdTarget);
				newChapter.setSubjectId(newSubject.getId()); 						// Using newly created subject id
				newChapter.setModifiedDate(new Date());
				newChapter.setSerialOrder(chapterOrder++);
				newChapter.setStatus(Chapter.STATUS_ACTIVE);
				
				newChapter = chapterDao.saveEntity(newChapter);
				
				// Getting Topic List
				List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
				topicOrder=1;
				for(Topic t : topicList)
				{
					// Saving topic
					Topic newTopic = new Topic();
					//newTopic.setNameUTF8(t.getNameUTF8());
					newTopic.setName(t.getName());
					newTopic.setChapterId(newChapter.getId());						// Using newly created chapter id
					newTopic.setSubjectId(newSubject.getId()); 						// Using newly created subject id
					newTopic.setGradeId(gradeIdTarget);							
					newTopic.setBoardId(boardIdTarget);			
					newTopic.setModifiedDate(new Date());
					newTopic.setSerialOrder(topicOrder++);
					newTopic.setStatus(Topic.STATUS_ACTIVE);
					
					newTopic = topicDao.saveEntity(newTopic);
					
					// Getting questionTopic List
					List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
					List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
					
					for(LearnObjective lolistdata : lolist)
					{
						
						
						
						
						// Saving QuestionTopic
						LearnObjective lol = new LearnObjective();
						lol.setBoardId(lolistdata.getBoardId());
						lol.setChapterId(lolistdata.getChapterId());
						lol.setGradeId(lolistdata.getGradeId());
						lol.setName(lolistdata.getName());
						lol.setStatus(lolistdata.getStatus());
						lol.setSubjectId(lolistdata.getSubjectId());
						lol.setTopicId(newTopic.getId());
						lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
						lODao.saveEntity(lol);
						/*newQuestionTopic.setLoId(qt.getLoId());
						newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());*/
						System.out.println("t.getId()old="+t.getId()+"=newTopic.getId()=="+newTopic.getId()+"==lolid=="+lol.getId());
					}
					 
					for(QuestionTopic qt : questionTopicList)
					{
						// Saving QuestionTopic
						QuestionTopic newQuestionTopic = new QuestionTopic();
						newQuestionTopic.setModifiedDate(new Date());
						newQuestionTopic.setQuestionId(qt.getQuestionId());
						newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
						newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
						newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
						newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
						newQuestionTopic.setLoId(qt.getLoId());
						newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
						questionTopicDao.saveEntity(newQuestionTopic);
					}
					
					// Getting moduleTopic List
					List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, t.getId(), ModuleTopic.STATUS_ACTIVE);
					
					for(ModuleTopic mt : moduleTopicList)
					{
						// Saving ModuleTopic
						ModuleTopic newModuleTopic = new ModuleTopic();
						newModuleTopic.setModifiedDate(new Date());
						newModuleTopic.setModuleId(mt.getModuleId());
						newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
						newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
						
						moduleTopicDao.saveEntity(newModuleTopic);
					}
				}
			}
		}
		
		return subjectSectionIdList;
	}

	@Transactional
	@Override
	public List<Long> addChapterMapping(String chapterIdString, String chapterSerialOrderString, String chapterNameString, Long boardIdTarget, Long subjectIdTarget, Long gradeIdTarget)				//
	{
		String[] chapterNames = chapterNameString.split("###");
		String[] chapterIds = chapterIdString.split(",");
		String[] chapterSerialOrders = chapterSerialOrderString.split(",");
		List<Long> chapterIdList = new ArrayList<Long>();
		for(int i=0;i<chapterIds.length;i++)
		{
			// Saving chapter
			Chapter newChapter = new Chapter();
			//newChapter.setNameUTF8(Utility.convertStringToByteUTF8(chapterNames[i]));
			newChapter.setName(chapterNames[i]);
			newChapter.setGradeId(gradeIdTarget); 						
			newChapter.setBoardId(boardIdTarget);
			newChapter.setSchoolId(null);
			newChapter.setSubjectId(subjectIdTarget);
			newChapter.setModifiedDate(new Date());
			newChapter.setSerialOrder(Integer.valueOf(chapterSerialOrders[i]));
			newChapter.setStatus(Chapter.STATUS_ACTIVE);
			
			newChapter = chapterDao.saveEntity(newChapter);
			
			chapterIdList.add(newChapter.getId());						// Adding id to list
			
			// Getting Topic List
			List<Topic> topicList = topicDao.getTopics(String.valueOf(chapterIds[i]), Topic.STATUS_ACTIVE);
			
			for(Topic t : topicList)
			{
				// Saving topic
				Topic newTopic = new Topic();
				//newTopic.setNameUTF8(t.getNameUTF8());
				newTopic.setName(t.getName());
				newTopic.setChapterId(newChapter.getId());				// Using newly created chapter id
				newTopic.setGradeId(gradeIdTarget);							
				newTopic.setBoardId(boardIdTarget);			
				newTopic.setSubjectId(subjectIdTarget);
				newTopic.setSchoolId(null);
				newTopic.setModifiedDate(new Date());
				newTopic.setSerialOrder(t.getSerialOrder());
				newTopic.setStatus(Topic.STATUS_ACTIVE);
				
				newTopic = topicDao.saveEntity(newTopic);
				
				// Getting questionTopic List
				List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
				
				List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
				
				for(LearnObjective lolistdata : lolist)
				{ 
					LearnObjective lol = new LearnObjective();
					lol.setBoardId(lolistdata.getBoardId());
					lol.setChapterId(lolistdata.getChapterId());
					lol.setGradeId(lolistdata.getGradeId());
					lol.setName(lolistdata.getName());
					lol.setStatus(lolistdata.getStatus());
					lol.setSubjectId(lolistdata.getSubjectId());
					lol.setTopicId(newTopic.getId());
					lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
					lODao.saveEntity(lol);
					/*newQuestionTopic.setLoId(qt.getLoId());
					newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());*/
					System.out.println("t.getId()old="+t.getId()+"=newTopic.getId()=="+newTopic.getId()+"==lolid=="+lol.getId());
				}
				
				for(QuestionTopic qt : questionTopicList)
				{
					// Saving QuestionTopic
					QuestionTopic newQuestionTopic = new QuestionTopic();
					newQuestionTopic.setModifiedDate(new Date());
					newQuestionTopic.setQuestionId(qt.getQuestionId());
					newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
					newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
					newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
					newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
					newQuestionTopic.setLoId(qt.getLoId());
					newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
					questionTopicDao.saveEntity(newQuestionTopic);
				}
				
				// Getting moduleTopic List
				List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, t.getId(), ModuleTopic.STATUS_ACTIVE);
				
				for(ModuleTopic mt : moduleTopicList)
				{
					// Saving ModuleTopic
					ModuleTopic newModuleTopic = new ModuleTopic();
					newModuleTopic.setModifiedDate(new Date());
					newModuleTopic.setModuleId(mt.getModuleId());
					newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
					newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
					
					moduleTopicDao.saveEntity(newModuleTopic);
				}
			}
		}
		
		return chapterIdList;
	}

	@Transactional
	@Override
 	public List<Long> addTopicMapping(String topicIdString, String topicSerialOrderString, String topicNameString, Long boardIdTarget, Long chapterIdTarget, Long subjectIdTarget, Long gradeIdTarget)
	{
		String[] topicNames = topicNameString.split("###");
		String[] topicIds = topicIdString.split(",");
		String[] topicSerialOrders = topicSerialOrderString.split(",");
		List<Long> topicIdList = new ArrayList<Long>();
		
		for(int i=0;i<topicIds.length;i++)
		{
			// Saving topic
			Topic newTopic = new Topic();
			//newTopic.setNameUTF8(Utility.convertStringToByteUTF8(topicNames[i]));
			newTopic.setName(topicNames[i]);
			newTopic.setChapterId(chapterIdTarget);				// Using newly created chapter id
			newTopic.setGradeId(gradeIdTarget);							
			newTopic.setBoardId(boardIdTarget);	
			newTopic.setSubjectId(subjectIdTarget);
			newTopic.setSchoolId(null);
			newTopic.setModifiedDate(new Date());
			newTopic.setSerialOrder(Integer.valueOf(topicSerialOrders[i]));
			newTopic.setStatus(Topic.STATUS_ACTIVE);
			
			newTopic = topicDao.saveEntity(newTopic);
		/*	while(true)
			{
				try
				{
					if(newTopic.getId()!=null)
						break;
				}
				catch (Exception e)
				{
					try
					{
						Thread.sleep(1);
					}
					catch (InterruptedException e1)
					{	
					}
				}
				
			}
			*/
			
			topicIdList.add(newTopic.getId());
			
			// Getting questionTopic List
			List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, Long.valueOf(topicIds[i]), Topic.STATUS_ACTIVE);
			
			
			List<LearnObjective> lolist = lODao.getLos(Long.valueOf(topicIds[i]).toString(), Topic.STATUS_ACTIVE);
			
			for(LearnObjective lolistdata : lolist)
			{ 
				LearnObjective lol = new LearnObjective();
				lol.setBoardId(lolistdata.getBoardId());
				lol.setChapterId(lolistdata.getChapterId());
				lol.setGradeId(lolistdata.getGradeId());
				lol.setName(lolistdata.getName());
				lol.setStatus(lolistdata.getStatus());
				lol.setSubjectId(lolistdata.getSubjectId());
				lol.setTopicId(newTopic.getId());
				lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
				lODao.saveEntity(lol);
				/*newQuestionTopic.setLoId(qt.getLoId());
				newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());*/
				System.out.println("t.getId()old="+Long.valueOf(topicIds[i]).toString()+"=newTopic.getId()=="+newTopic.getId()+"==lolid=="+lol.getId());
			}
			
			for(QuestionTopic qt : questionTopicList)
			{
				// Saving QuestionTopic
				QuestionTopic newQuestionTopic = new QuestionTopic();
				newQuestionTopic.setModifiedDate(new Date());
				newQuestionTopic.setQuestionId(qt.getQuestionId());
				newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
				newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
				newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
				newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
				newQuestionTopic.setLoId(qt.getLoId());
				newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
				questionTopicDao.saveEntity(newQuestionTopic);
			}
			
			// Getting moduleTopic List
			List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, Long.valueOf(topicIds[i]), ModuleTopic.STATUS_ACTIVE);
			
			for(ModuleTopic mt : moduleTopicList)
			{
				// Saving ModuleTopic
				ModuleTopic newModuleTopic = new ModuleTopic();
				newModuleTopic.setModifiedDate(new Date());
				newModuleTopic.setModuleId(mt.getModuleId());
				newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
				newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
				
				moduleTopicDao.saveEntity(newModuleTopic);
			}
		}
		return topicIdList;
	}
	
	
	
	@Transactional
	@Override
	public String addGradeAndSectionMappingExistedSync(Long sectionIdSource, Long boardIdTarget, Long gradeIdTarget, Long sectionIdTarget)
	{
		
		List<SubjectSection> subjectSectionList1 = subjectSectionDao.getSubjectSections(String.valueOf(sectionIdSource),1);
		
		List<SubjectSection> subjectSectionList2 = subjectSectionDao.getSubjectSections(String.valueOf(sectionIdTarget),1);
		
		
		Map<Long,String> subdetail=new HashMap<Long,String>();
		
		
		
		
		
		
		
		
		Map<String,Date> ctls_map_date=getDataDate(subjectSectionList1);
		
		Map<String,List<Long>> speci_map_IdList=getDataDate2(subjectSectionList2);
		
		
		Map<String,Map<Long,String>> speci_map_IdList22=getDataDate_allId2(subjectSectionList2);
		
		
		System.out.println(speci_map_IdList22);
		
		Map<Long,String> lomap=speci_map_IdList22.get("lo");
		
		

		
		
		
		for(SubjectSection ss : subjectSectionList1)
		{	
			// Saving subject
			Subject newSubject;
			
			if(speci_map_IdList.get("subject").contains(ss.getSubject().getId()))
			{
				newSubject = ss.getSubject();
				newSubject = subjectDao.saveOrUpdateEntity(newSubject);
				
			}else
			{
				newSubject = new Subject();
				newSubject.setBoardId(boardIdTarget);
				newSubject.setSchoolId(null);
				newSubject.setModifiedDate(new Date());
				newSubject.setName(ss.getSubject().getName());
				newSubject.setStatus(Subject.STATUS_ACTIVE);
				if(ss.getSubject().getSubImage()!=null){newSubject.setSubImage(ss.getSubject().getSubImage());}
				newSubject = subjectDao.saveEntity(newSubject);
				
			} 
			
			
			//Saving subjectSection mapping
			SubjectSection newSubjectSection;
			if(speci_map_IdList.get("subjectsection").contains(ss.getId())){
			newSubjectSection = ss;
			//newSubjectSection = subjectSectionDao.saveOrUpdateEntity(newSubjectSection);
			
			}else{
				
				newSubjectSection = new SubjectSection(); 
				newSubjectSection.setModifiedDate(new Date());
				newSubjectSection.setSectionId(sectionIdTarget);			
				newSubjectSection.setSubjectId(newSubject.getId());						// Using newly created subject id
				newSubjectSection.setStatus(SubjectSection.STATUS_ACTIVE);
				//newSubjectSection = subjectSectionDao.saveEntity(newSubjectSection);
				
			}
			
			
			// Getting Chapter List
			List<Chapter> chapterList =  chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);		
			
			for(Chapter c : chapterList)
			{
				// Saving chapter
			

				Chapter newChapter;
				
				if(speci_map_IdList.get("chapter").contains(c.getId())){
					newChapter = c;
					//newChapter = chapterDao.saveOrUpdateEntity(newChapter);
				}else{
				
					System.out.println("----------------------------"+newSubject.getId());
					System.out.println("----------------------------"+newSubject.getRefSubjectId());
				
					
					
					newChapter = new Chapter();
					newChapter.setName(c.getName());
					newChapter.setGradeId(gradeIdTarget); 						
					newChapter.setSubjectId(newSubject.getId()); 	// Using newly created subject id
					newChapter.setBoardId(boardIdTarget);
					newChapter.setSchoolId(null);
					newChapter.setRefChapterId(c.getId());
					
					newChapter.setModifiedDate(new Date());
					newChapter.setStatus(Chapter.STATUS_ACTIVE);
					//newChapter = chapterDao.saveEntity(newChapter);
					
					
					
				}
				
				
				
				
				
				
				// Getting Topic List
				List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
				
				
	
				for(Topic t : topicList)
				{
					// Saving topic
					Topic newTopic;
					
					if(speci_map_IdList.get("topic").contains(t.getId())){
						
						newTopic = t;
						//newTopic = topicDao.saveOrUpdateEntity(newTopic);
						
					}else{
						
						newTopic = new Topic();
						newTopic.setName(t.getName());
						newTopic.setGradeId(gradeIdTarget);
						newTopic.setChapterId(newChapter.getId());						// Using newly created chapter id
						newTopic.setSubjectId(newSubject.getId()); 						// Using newly created subject id
						newTopic.setBoardId(boardIdTarget);		
						newTopic.setSchoolId(null);
						newTopic.setModifiedDate(new Date());
						newTopic.setStatus(Topic.STATUS_ACTIVE);
						//newTopic = topicDao.saveEntity(newTopic);
						
					}
					
					
					
					// Getting questionTopic List
					List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
					
					List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
					
					for(LearnObjective lolistdata : lolist)
					{
						
						LearnObjective lol;
						
						// Saving QuestionTopic
						if(speci_map_IdList.get("lo").contains(lolistdata.getId())){
						
							lol = lolistdata;
							//lODao.saveOrUpdateEntity(lol);
							
							
						}else
						{
							
							lol = new LearnObjective();
							lol.setId(lolistdata.getId());
							lol.setBoardId(lolistdata.getBoardId());
							lol.setChapterId(lolistdata.getChapterId());
							lol.setGradeId(lolistdata.getGradeId());
							lol.setName(lolistdata.getName());
							lol.setStatus(lolistdata.getStatus());
							lol.setSubjectId(lolistdata.getSubjectId());
							lol.setTopicId(newTopic.getId());
							lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
							//lODao.saveEntity(lol);
							
						}
						
						
					}
					 
					for(QuestionTopic qt : questionTopicList)
					{
						// Saving QuestionTopic
						QuestionTopic newQuestionTopic;
						if(speci_map_IdList.get("questiontopic").contains(qt.getId())){
							
							newQuestionTopic = qt;
							//questionTopicDao.saveOrUpdateEntity(newQuestionTopic);
						
						}
						else
						{
							newQuestionTopic = new QuestionTopic();
							newQuestionTopic.setModifiedDate(new Date());
							newQuestionTopic.setQuestionId(qt.getQuestionId());
							newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
							newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
							newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
							newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
							newQuestionTopic.setLoId(qt.getLoId());
							newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
							//questionTopicDao.saveEntity(newQuestionTopic);
							
						}
						
						
					}
					
					
				}
			}
		}
		
		
		
		
		
		
		/*
		
		
		
		for(SubjectSection ss : subjectSectionList1)
		{	
			// Saving subject
			
			Subject newSubject;
			if(speci_map_IdList.get("subject").contains(ss.getSubject().getId()))
			{
				newSubject = ss.getSubject();
				newSubject = subjectDao.saveOrUpdateEntity(newSubject);
				
			}else
			{
				newSubject = new Subject();
				newSubject.setBoardId(boardIdTarget);
				newSubject.setSchoolId(null);
				newSubject.setModifiedDate(new Date());
				newSubject.setName(ss.getSubject().getName());
				newSubject.setStatus(Subject.STATUS_ACTIVE);
				if(ss.getSubject().getSubImage()!=null){newSubject.setSubImage(ss.getSubject().getSubImage());}
				newSubject = subjectDao.saveEntity(newSubject);
				
			} 
			
			
			//Saving subjectSection mapping
			SubjectSection newSubjectSection;
			if(speci_map_IdList.get("subjectsection").contains(ss.getId())){
			newSubjectSection = ss;
			//newSubjectSection = subjectSectionDao.saveOrUpdateEntity(newSubjectSection);
			
			}else{
				
				newSubjectSection = new SubjectSection(); 
				newSubjectSection.setModifiedDate(new Date());
				newSubjectSection.setSectionId(sectionIdTarget);			
				newSubjectSection.setSubjectId(newSubject.getId());						// Using newly created subject id
				newSubjectSection.setStatus(SubjectSection.STATUS_ACTIVE);
				//newSubjectSection = subjectSectionDao.saveEntity(newSubjectSection);
				
			}
			
			
			// Getting Chapter List
			List<Chapter> chapterList =  chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);		
			
			for(Chapter c : chapterList)
			{
				// Saving chapter
			

				Chapter newChapter;
				
				if(speci_map_IdList.get("chapter").contains(c.getId())){
					newChapter = c;
					//newChapter = chapterDao.saveOrUpdateEntity(newChapter);
				}else{
					
					
					
					
					
					
					newChapter = new Chapter();
					newChapter.setName(c.getName());
					newChapter.setGradeId(gradeIdTarget); 						
					newChapter.setSubjectId(newSubject.getId()); 	// Using newly created subject id
					newChapter.setBoardId(boardIdTarget);
					newChapter.setSchoolId(null);
					newChapter.setRefChapterId(c.getId());
					
					newChapter.setModifiedDate(new Date());
					newChapter.setStatus(Chapter.STATUS_ACTIVE);
					//newChapter = chapterDao.saveEntity(newChapter);
					
					
					
					
				
					
					
					
				}
				
				
				
				
				
				
				// Getting Topic List
				List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
				
				
	
				for(Topic t : topicList)
				{
					// Saving topic
					Topic newTopic;
					
					if(speci_map_IdList.get("topic").contains(t.getId())){
						
						newTopic = t;
						//newTopic = topicDao.saveOrUpdateEntity(newTopic);
						
					}else{
						
						newTopic = new Topic();
						newTopic.setName(t.getName());
						newTopic.setGradeId(gradeIdTarget);
						newTopic.setChapterId(newChapter.getId());						// Using newly created chapter id
						newTopic.setSubjectId(newSubject.getId()); 						// Using newly created subject id
						newTopic.setBoardId(boardIdTarget);		
						newTopic.setSchoolId(null);
						newTopic.setModifiedDate(new Date());
						newTopic.setStatus(Topic.STATUS_ACTIVE);
						//newTopic = topicDao.saveEntity(newTopic);
						
					}
					
					
					
					// Getting questionTopic List
					List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
					
					List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
					
					for(LearnObjective lolistdata : lolist)
					{
						
						LearnObjective lol;
						
						// Saving QuestionTopic
						if(speci_map_IdList.get("lo").contains(lolistdata.getId())){
						
							lol = lolistdata;
							//lODao.saveOrUpdateEntity(lol);
							
							
						}else
						{
							
							lol = new LearnObjective();
							lol.setId(lolistdata.getId());
							lol.setBoardId(lolistdata.getBoardId());
							lol.setChapterId(lolistdata.getChapterId());
							lol.setGradeId(lolistdata.getGradeId());
							lol.setName(lolistdata.getName());
							lol.setStatus(lolistdata.getStatus());
							lol.setSubjectId(lolistdata.getSubjectId());
							lol.setTopicId(newTopic.getId());
							lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
							//lODao.saveEntity(lol);
							
						}
						
						
					}
					 
					for(QuestionTopic qt : questionTopicList)
					{
						// Saving QuestionTopic
						QuestionTopic newQuestionTopic;
						if(speci_map_IdList.get("questiontopic").contains(qt.getId())){
							
							newQuestionTopic = qt;
							//questionTopicDao.saveOrUpdateEntity(newQuestionTopic);
						
						}
						else
						{
							newQuestionTopic = new QuestionTopic();
							newQuestionTopic.setModifiedDate(new Date());
							newQuestionTopic.setQuestionId(qt.getQuestionId());
							newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
							newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
							newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
							newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
							newQuestionTopic.setLoId(qt.getLoId());
							newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
							//questionTopicDao.saveEntity(newQuestionTopic);
							
						}
						
						
					}
					
					
				}
			}
		}
		
		*/
		
	
		
		
		
		return "success";
	}
	
	
	
	private Map<String,Map<Long,String>> getDataDate_allId2(List<SubjectSection> subjectSectionList)
	{
		
					
				 Map<Long,String> subjectId = new HashMap<Long,String>();
				 Map<Long,String> subjectsectionId = new HashMap<Long,String>();
				 Map<Long,String> chapterId = new HashMap<Long,String>();
				 Map<Long,String> topicId = new HashMap<Long,String>();
				 Map<Long,String> loId = new HashMap<Long,String>();
				 Map<Long,String> questiontopicId = new HashMap<Long,String>();
				 Map<Long,String> schoolId = new HashMap<Long,String>();
				 
				
				 
				 
				 
				 
				 
				
				for(SubjectSection ss : subjectSectionList)
				{
					
					//refid+oldsubjectId
					subjectId.put(ss.getSubject().getRefSubjectId(),ss.getSubject().getRefSubjectId()+"#"+ss.getSubject().getId());
					subjectsectionId.put(ss.getRefSubjectSectionId(),ss.getRefSubjectSectionId()+"#"+ss.getId());
					
					
					
					List<Chapter> chapterList =  chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);	
					
					
					
					for(Chapter c : chapterList)
					{	
						
						//chapter ref+subject id+ chapter id
						chapterId.put(c.getRefChapterId(),c.getRefChapterId()+"#"+ss.getSubject().getId()+"#"+c.getId());
						
						
						schoolId.put(c.getSchoolId(),""+c.getSchoolId());
						
						
						
						
						List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
						//System.out.println("============topicList============="+topicList.size());
						
						for(Topic t : topicList)
						{
							//topic ref+subject id+ chapter id+topic id
							topicId.put(t.getRefTopicId(),t.getRefTopicId()+"#"+ss.getSubject().getId()+"#"+c.getId()+"#"+t.getId());
							
							List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
							
							
							//System.out.println("============lolist============="+lolist.size());
							
							for(LearnObjective lo : lolist)
							{	
								//topic ref+subject id+ chapter id+topic id+lo id
								
								loId.put(lo.getRefLearnObjective(),lo.getRefLearnObjective()+"#"+ss.getSubject().getId()+"#"+c.getId()+"#"+t.getId()+"#"+lo.getId());
								
							}
							
							List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
							
							//System.out.println("============questionTopicList============="+questionTopicList.size());
							for(QuestionTopic qt : questionTopicList)
							{
								
								
								//qt ref+subject id+ chapter id+topic id
								
								
								questiontopicId.put(qt.getRefQuestiontopicId(),qt.getRefQuestiontopicId()+"#"+ss.getSubject().getId()+"#"+c.getId()+"#"+t.getId()+"#"+qt.getId());
								
								
							}
						}
					}
				}
				
				
				Map<String,Map<Long,String>> maxdate=new HashMap<String,Map<Long,String>>();
				/*maxdate.put("subject", Collections.max(subjectdates));
				maxdate.put("chapter", Collections.max(chapterdates));
				maxdate.put("topic", Collections.max(topicdates));
				maxdate.put("lo", Collections.max(lodates));
				maxdate.put("questiontopic", Collections.max(questiontopicdates));
				maxdate.put("question", Collections.max(questiondates));*/
				
				
				maxdate.put("subject", subjectId);
				maxdate.put("subjectsection", subjectsectionId);
				maxdate.put("chapter", chapterId);
				maxdate.put("topic", topicId);
				maxdate.put("lo", loId);
				maxdate.put("questiontopic", questiontopicId);
				
				
				
				
				
				
		return maxdate;
	}
	
	
	/**
	 * 
	 * @param sectionid
	 * @param status
	 * @return
	 */
	
	private Map<String,List<Long>> getDataDate2(List<SubjectSection> subjectSectionList)
	{
		
					
				 List<Long> subjectId = new ArrayList<Long>();
				 List<Long> subjectsectionId = new ArrayList<Long>();
				 List<Long> chapterId = new ArrayList<Long>();
				 List<Long> topicId = new ArrayList<Long>();
				 List<Long> loId = new ArrayList<Long>();
				 List<Long> questiontopicId = new ArrayList<Long>();
				 List<Long> schoolId = new ArrayList<Long>();
				 
				
				 
				 
				 
				 
				 
				
				for(SubjectSection ss : subjectSectionList)
				{
					
					subjectId.add(ss.getSubject().getRefSubjectId());
					subjectsectionId.add(ss.getRefSubjectSectionId());
					
					
					
					List<Chapter> chapterList =  chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);	
					
					
					
					for(Chapter c : chapterList)
					{	
						chapterId.add(c.getRefChapterId());
						schoolId.add(c.getSchoolId());
						
						
						
						
						List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
						//System.out.println("============topicList============="+topicList.size());
						
						for(Topic t : topicList)
						{
							
							topicId.add(t.getRefTopicId());
							List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
							
							
							//System.out.println("============lolist============="+lolist.size());
							
							for(LearnObjective lo : lolist)
							{	
								loId.add(lo.getRefLearnObjective());
								
							}
							
							List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
							
							//System.out.println("============questionTopicList============="+questionTopicList.size());
							for(QuestionTopic qt : questionTopicList)
							{
								questiontopicId.add(qt.getRefQuestiontopicId());
								
								
							}
						}
					}
				}
				
				
				Map<String,List<Long>> maxdate=new HashMap<String,List<Long>>();
				/*maxdate.put("subject", Collections.max(subjectdates));
				maxdate.put("chapter", Collections.max(chapterdates));
				maxdate.put("topic", Collections.max(topicdates));
				maxdate.put("lo", Collections.max(lodates));
				maxdate.put("questiontopic", Collections.max(questiontopicdates));
				maxdate.put("question", Collections.max(questiondates));*/
				
				
				maxdate.put("subject", subjectId);
				maxdate.put("subjectsection", subjectsectionId);
				maxdate.put("chapter", chapterId);
				maxdate.put("topic", topicId);
				maxdate.put("lo", loId);
				maxdate.put("questiontopic", questiontopicId);
				
				
				
				
				
				
		return maxdate;
	}
	
	
	
	

	
	private Map<String,Date> getDataDate(List<SubjectSection> subjectSectionList)
	{
		
				// Getting SubjectSection mapping
				
			
				
				
				 List<Date> subjectdates = new ArrayList<Date>();
				 List<Date> chapterdates = new ArrayList<Date>();
				 List<Date> topicdates = new ArrayList<Date>();
				 List<Date> lodates = new ArrayList<Date>();
				 List<Date> questiontopicdates = new ArrayList<Date>();
				 List<Date> questiondates = new ArrayList<Date>();
				 
				 
				 
				
				for(SubjectSection ss : subjectSectionList)
				{
					
					subjectdates.add(ss.getSubject().getInsertDate());
					List<Chapter> chapterList =  chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);	
					
					for(Chapter c : chapterList)
					{
					
						chapterdates.add(c.getInsertDate());
						List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
						for(Topic t : topicList)
						{
							
							topicdates.add(t.getInsertDate());
							List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
							
							for(LearnObjective lo : lolist)
							{	
								lodates.add(lo.getInsertDate());
								
							}
							
							List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
							
						
							
							for(QuestionTopic qt : questionTopicList)
							{
								questiontopicdates.add(qt.getInsertDate());
								questiondates.add(qt.getQuestion().getInsertDate());
								
								
								
								
							}
						}
					}
				}
				
				
				Map<String,Date> maxdate=new HashMap<String,Date>();
				maxdate.put("subject", Collections.max(subjectdates));
				maxdate.put("chapter", Collections.max(chapterdates));
				maxdate.put("topic", Collections.max(topicdates));
				maxdate.put("lo", Collections.max(lodates));
				maxdate.put("questiontopic", Collections.max(questiontopicdates));
				maxdate.put("question", Collections.max(questiondates));
				
				
				
		return maxdate;
	}
	
	
	

	@Transactional
	@Override
	public String addGradeAndSectionMappingExisted(Long sectionIdSource, Long boardIdTarget, Long gradeIdTarget, Long sectionIdTarget)
	{
		// Getting SubjectSection mapping
		List<SubjectSection> subjectSectionList = subjectSectionDao.getSubjectSections(String.valueOf(sectionIdSource), SubjectSection.STATUS_ACTIVE);
		
		int topicOrder,chapterOrder;
		for(SubjectSection ss : subjectSectionList)
		{
			// Saving subject
			Subject newSubject = new Subject();
			newSubject.setBoardId(boardIdTarget);
			newSubject.setSchoolId(null);
			newSubject.setModifiedDate(new Date());
			newSubject.setName(ss.getSubject().getName());
			newSubject.setStatus(Subject.STATUS_ACTIVE);
			if(ss.getSubject().getSubImage()!=null){
				newSubject.setSubImage(ss.getSubject().getSubImage());
			}
			newSubject = subjectDao.saveEntity(newSubject);
			
			// Saving subjectSection mapping
			SubjectSection newSubjectSection = new SubjectSection();
			newSubjectSection.setModifiedDate(new Date());
			newSubjectSection.setSectionId(sectionIdTarget);			
			newSubjectSection.setSubjectId(newSubject.getId());						// Using newly created subject id
			newSubjectSection.setStatus(SubjectSection.STATUS_ACTIVE);
			
			newSubjectSection = subjectSectionDao.saveEntity(newSubjectSection);
			
			// Getting Chapter List
			List<Chapter> chapterList =  chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);		
			chapterOrder=1;
			for(Chapter c : chapterList)
			{
				// Saving chapter
				Chapter newChapter = new Chapter();
				//newChapter.setNameUTF8(c.getNameUTF8());
				newChapter.setName(c.getName());
				newChapter.setGradeId(gradeIdTarget); 						
				newChapter.setSubjectId(newSubject.getId()); 						// Using newly created subject id
				newChapter.setBoardId(boardIdTarget);
				newChapter.setSchoolId(null);
				newChapter.setModifiedDate(new Date());
				newChapter.setSerialOrder(chapterOrder++);
				newChapter.setStatus(Chapter.STATUS_ACTIVE);
				
				newChapter = chapterDao.saveEntity(newChapter);
				
				// Getting Topic List
				List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
				topicOrder=1;
				for(Topic t : topicList)
				{
					// Saving topic
					Topic newTopic = new Topic();
					//newTopic.setNameUTF8(t.getNameUTF8());
					newTopic.setName(t.getName());
					newTopic.setGradeId(gradeIdTarget);
					newTopic.setChapterId(newChapter.getId());						// Using newly created chapter id
					newTopic.setSubjectId(newSubject.getId()); 						// Using newly created subject id
					newTopic.setBoardId(boardIdTarget);		
					newTopic.setSchoolId(null);
					newTopic.setModifiedDate(new Date());
					newTopic.setSerialOrder(topicOrder++);
					newTopic.setStatus(Topic.STATUS_ACTIVE);
					
					newTopic = topicDao.saveEntity(newTopic);
					
					
					
					
					
					// Getting questionTopic List
					List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
					
					List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
					
					for(LearnObjective lolistdata : lolist)
					{
						// Saving QuestionTopic
						LearnObjective lol = new LearnObjective();
						lol.setBoardId(lolistdata.getBoardId());
						lol.setChapterId(lolistdata.getChapterId());
						lol.setGradeId(lolistdata.getGradeId());
						lol.setName(lolistdata.getName());
						lol.setStatus(lolistdata.getStatus());
						lol.setSubjectId(lolistdata.getSubjectId());
						lol.setTopicId(newTopic.getId());
						lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
						lODao.saveEntity(lol);
					}
					 
					for(QuestionTopic qt : questionTopicList)
					{
						// Saving QuestionTopic
						QuestionTopic newQuestionTopic = new QuestionTopic();
						newQuestionTopic.setModifiedDate(new Date());
						newQuestionTopic.setQuestionId(qt.getQuestionId());
						newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
						newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
						newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
						newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
						newQuestionTopic.setLoId(qt.getLoId());
						newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
						
						
						questionTopicDao.saveEntity(newQuestionTopic);
					}
					
					
					
				 
					
					
					// Getting moduleTopic List
					List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, t.getId(), ModuleTopic.STATUS_ACTIVE);
					
					for(ModuleTopic mt : moduleTopicList)
					{
						// Saving ModuleTopic
						ModuleTopic newModuleTopic = new ModuleTopic();
						newModuleTopic.setModifiedDate(new Date());
						newModuleTopic.setModuleId(mt.getModuleId());
						newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
						newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
						
						moduleTopicDao.saveEntity(newModuleTopic);
					}
					
					
				}
			}
		}
		
		return "success";
	}

	@Transactional
	@Override
	public String addSubjectAndSubjectSectionMappingExisted(Long gradeIdSource, Long subjectIdSource, Long boardIdTarget, Long gradeIdTarget, Long subjectIdTarget)
	{
		// Getting Chapter List
		List<Chapter> chapterList = chapterDao.getChapters(String.valueOf(subjectIdSource), gradeIdSource, Chapter.STATUS_ACTIVE);
		int chapterOrder=1,topicOrder;
		for(Chapter c : chapterList)
		{
			// Saving chapter
			Chapter newChapter = new Chapter();
			//newChapter.setNameUTF8(c.getNameUTF8());
			newChapter.setName(c.getName());
			newChapter.setGradeId(gradeIdTarget); 						
			newChapter.setBoardId(boardIdTarget);
			newChapter.setSubjectId(subjectIdTarget); 						
			newChapter.setModifiedDate(new Date());
			newChapter.setSerialOrder(chapterOrder++);
			newChapter.setStatus(Chapter.STATUS_ACTIVE);
			
			newChapter = chapterDao.saveEntity(newChapter);
			
			// Getting Topic List
			List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
			topicOrder=1;
			for(Topic t : topicList)
			{
				// Saving topic
				Topic newTopic = new Topic();
				//newTopic.setNameUTF8(t.getNameUTF8());
				newTopic.setName(t.getName());
				newTopic.setChapterId(newChapter.getId());						// Using newly created chapter id
				newTopic.setSubjectId(subjectIdTarget); 						
				newTopic.setGradeId(gradeIdTarget);							
				newTopic.setBoardId(boardIdTarget);			
				newTopic.setModifiedDate(new Date());
				newTopic.setSerialOrder(topicOrder++);
				newTopic.setStatus(Topic.STATUS_ACTIVE);
				
				newTopic = topicDao.saveEntity(newTopic);
				
				// Getting questionTopic List
				List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);

				List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
				
				for(LearnObjective lolistdata : lolist)
				{ 
					LearnObjective lol = new LearnObjective();
					lol.setBoardId(lolistdata.getBoardId());
					lol.setChapterId(lolistdata.getChapterId());
					lol.setGradeId(lolistdata.getGradeId());
					lol.setName(lolistdata.getName());
					lol.setStatus(lolistdata.getStatus());
					lol.setSubjectId(lolistdata.getSubjectId());
					lol.setTopicId(newTopic.getId());
					lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
					lODao.saveEntity(lol);
					/*newQuestionTopic.setLoId(qt.getLoId());
					newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());*/
					System.out.println("t.getId()old="+Long.valueOf(t.getId().toString())+"=newTopic.getId()=="+newTopic.getId()+"==lolid=="+lol.getId());
				}
				for(QuestionTopic qt : questionTopicList)
				{
					// Saving QuestionTopic
					QuestionTopic newQuestionTopic = new QuestionTopic();
					newQuestionTopic.setModifiedDate(new Date());
					newQuestionTopic.setQuestionId(qt.getQuestionId());
					newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
					newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
					newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
					newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
					newQuestionTopic.setLoId(qt.getLoId());
					newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
					questionTopicDao.saveEntity(newQuestionTopic);
				}
				
				// Getting moduleTopic List
				List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, t.getId(), ModuleTopic.STATUS_ACTIVE);
				
				for(ModuleTopic mt : moduleTopicList)
				{
					// Saving ModuleTopic
					ModuleTopic newModuleTopic = new ModuleTopic();
					newModuleTopic.setModifiedDate(new Date());
					newModuleTopic.setModuleId(mt.getModuleId());
					newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
					newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
					
					moduleTopicDao.saveEntity(newModuleTopic);
				}
			}
		}
		return "success";
	}

	@Transactional
	@Override
	public String addChapterMappingExisted(Long chapterIdSource, Long boardIdTarget, Long gradeIdTarget, Long subjectIdTarget, Long chapterIdTarget)
	{
		// Getting Topic List
		List<Topic> topicList = topicDao.getTopics(String.valueOf(chapterIdSource), Topic.STATUS_ACTIVE);
		int topicOrder=1;
		for(Topic t : topicList)
		{
			// Saving topic
			Topic newTopic = new Topic();
			//newTopic.setNameUTF8(t.getNameUTF8());
			newTopic.setName(t.getName());
			newTopic.setChapterId(chapterIdTarget);			
			newTopic.setGradeId(gradeIdTarget);							
			newTopic.setBoardId(boardIdTarget);			
			newTopic.setSubjectId(subjectIdTarget);
			newTopic.setSchoolId(null);
			newTopic.setModifiedDate(new Date());
			newTopic.setSerialOrder(topicOrder++);
			newTopic.setStatus(Topic.STATUS_ACTIVE);
			
			newTopic = topicDao.saveEntity(newTopic);
			
			// Getting questionTopic List
			List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
			List<LearnObjective> lolist = lODao.getLos(t.getId().toString(), Topic.STATUS_ACTIVE);
			
			for(LearnObjective lolistdata : lolist)
			{ 
				LearnObjective lol = new LearnObjective();
				lol.setBoardId(lolistdata.getBoardId());
				lol.setChapterId(lolistdata.getChapterId());
				lol.setGradeId(lolistdata.getGradeId());
				lol.setName(lolistdata.getName());
				lol.setStatus(lolistdata.getStatus());
				lol.setSubjectId(lolistdata.getSubjectId());
				lol.setTopicId(newTopic.getId());
				lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
				lODao.saveEntity(lol);
				/*newQuestionTopic.setLoId(qt.getLoId());
				newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());*/
				System.out.println("t.getId()old="+Long.valueOf(t.getId().toString())+"=newTopic.getId()=="+newTopic.getId()+"==lolid=="+lol.getId());
			}
			for(QuestionTopic qt : questionTopicList)
			{
				// Saving QuestionTopic
				QuestionTopic newQuestionTopic = new QuestionTopic();
				newQuestionTopic.setModifiedDate(new Date());
				newQuestionTopic.setQuestionId(qt.getQuestionId());
				newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
				newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
				newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
				newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
				newQuestionTopic.setLoId(qt.getLoId());
				newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
				questionTopicDao.saveEntity(newQuestionTopic);
			}
			
			// Getting moduleTopic List
			List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, t.getId(), ModuleTopic.STATUS_ACTIVE);
			
			for(ModuleTopic mt : moduleTopicList)
			{
				// Saving ModuleTopic
				ModuleTopic newModuleTopic = new ModuleTopic();
				newModuleTopic.setModifiedDate(new Date());
				newModuleTopic.setModuleId(mt.getModuleId());
				newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
				newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
				
				moduleTopicDao.saveEntity(newModuleTopic);
			}
		}
		
		return "success";
	}

	@Transactional
	@Override
	public String addTopicMappingExisted(Long topicIdSource, Long boardIdTarget, Long gradeIdTarget, Long subjectIdTarget, Long chapterIdTarget, Long topicIdTarget)
	{
		// Getting questionTopic List
		List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, topicIdSource, Topic.STATUS_ACTIVE);
		List<LearnObjective> lolist = lODao.getLos(topicIdSource.toString(), Topic.STATUS_ACTIVE);
		
		for(LearnObjective lolistdata : lolist)
		{ 
			LearnObjective lol = new LearnObjective();
			lol.setBoardId(lolistdata.getBoardId());
			lol.setChapterId(lolistdata.getChapterId());
			lol.setGradeId(lolistdata.getGradeId());
			lol.setName(lolistdata.getName());
			lol.setStatus(lolistdata.getStatus());
			lol.setSubjectId(lolistdata.getSubjectId());
			lol.setTopicId(topicIdTarget);
			lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
			lODao.saveEntity(lol);
			/*newQuestionTopic.setLoId(qt.getLoId());
			newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());*/
			System.out.println("t.getId()old="+topicIdSource.toString()+"=newTopic.getId()=="+topicIdTarget+"==lolid=="+lol.getId());
		}
		for(QuestionTopic qt : questionTopicList)
		{
			// Saving QuestionTopic
			QuestionTopic newQuestionTopic = new QuestionTopic();
			newQuestionTopic.setModifiedDate(new Date());
			newQuestionTopic.setQuestionId(qt.getQuestionId());
			newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
			newQuestionTopic.setTopicId(topicIdTarget); 				
			newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
			newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
			newQuestionTopic.setLoId(qt.getLoId());
			newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
			questionTopicDao.saveEntity(newQuestionTopic);
		}
		
		// Getting moduleTopic List
		List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, topicIdSource, ModuleTopic.STATUS_ACTIVE);
		
		for(ModuleTopic mt : moduleTopicList)
		{
			// Saving ModuleTopic
			ModuleTopic newModuleTopic = new ModuleTopic();
			newModuleTopic.setModifiedDate(new Date());
			newModuleTopic.setModuleId(mt.getModuleId());
			newModuleTopic.setTopicId(topicIdTarget); 				
			newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
			
			moduleTopicDao.saveEntity(newModuleTopic);
		}
		return "success";
	}

	@Transactional
	@Override
	public String addSchoolAndBoardMapping(Long boardId, Long schoolId, Long currentSessionId)
	{
		School school = schoolDao.getSchools(String.valueOf(schoolId), School.STATUS_ACTIVE).get(0);
		school.setBoardId(boardId);
		school = schoolDao.updateEntity(school);
		
		// Getting sections
		List<Section> sectionList = sectionDao.getSections(null, boardId, null, Section.STATUS_ACTIVE, null, "asc");
		int chapterOrder;
		int topicOrder;
		for(Section s : sectionList)
		{
			// Saving grade
			Grade newGrade = new Grade();
			newGrade.setSchoolId(schoolId);
			newGrade.setName(s.getGrade().getName());
			newGrade.setBoardId(null);
			newGrade.setModifiedDate(new Date());
			newGrade.setStatus(Grade.STATUS_ACTIVE);
			
			newGrade = gradeDao.saveEntity(newGrade);
			
		
			
			// Saving SessionSchoolGrade
			SessionSchoolGrade newSessionSchoolGrade = new SessionSchoolGrade();
			newSessionSchoolGrade.setSessionId(currentSessionId);
			newSessionSchoolGrade.setSchoolId(schoolId);
			newSessionSchoolGrade.setGradeId(newGrade.getId());						// Using newly created grade id
			newSessionSchoolGrade.setModifiedDate(new Date());
			newSessionSchoolGrade.setStatus(SessionSchoolGrade.STATUS_ACTIVE);
			
			newSessionSchoolGrade = sessionSchoolGradeDao.saveEntity(newSessionSchoolGrade);
			
			// Saving section
			Section newSection = new Section();
			newSection.setSessionSchoolGradeId(newSessionSchoolGrade.getId()); 		// Using newly created SessionSchoolGrade id
			newSection.setBoardId(null);
			newSection.setGradeId(null); 
			newSection.setName(s.getName());
			newSection.setModifiedDate(new Date());
			newSection.setStatus(Section.STATUS_ACTIVE);
			
			newSection = sectionDao.saveEntity(newSection);
			
			// Getting SubjectSection list
			List<SubjectSection> subjectSectionList = subjectSectionDao.getSubjectSections(String.valueOf(s.getId()), SubjectSection.STATUS_ACTIVE);
			
			for(SubjectSection ss : subjectSectionList)
			{
				List<Subject> subExistedList = subjectDao.getSubjects(null, schoolId, ss.getSubject().getName().toString(), Subject.STATUS_ACTIVE);
				
				Subject newSubject = null;
				if(subExistedList == null || subExistedList.size() == 0)							// If subject doesn't already exist
				{
					// Saving subject													
					newSubject = new Subject();
					newSubject.setName(ss.getSubject().getName());
					newSubject.setRefSubjectId(ss.getSubject().getId());
					newSubject.setSchoolId(schoolId);
					newSubject.setBoardId(null); 
					newSubject.setStatus(Subject.STATUS_ACTIVE);
					newSubject.setModifiedDate(new Date());
					if(ss.getSubject().getSubImage()!=null){
						newSubject.setSubImage(ss.getSubject().getSubImage());
					}
					newSubject = subjectDao.saveEntity(newSubject);
				}
				else																// If subject exists already
				{
					
					newSubject = subExistedList.get(0);
					if(newSubject.getSubImage()==null && ss.getSubject().getSubImage()!=null){
						newSubject.setSubImage(ss.getSubject().getSubImage());
						newSubject.setRefSubjectId(ss.getSubject().getId());
						newSubject = subjectDao.saveEntity(newSubject);
					}
				}
				
				// Saving SubjectSection
				SubjectSection newSubjectSection = new SubjectSection();
				newSubjectSection.setSubjectId(newSubject.getId()); 				// Using newly created subject id
				newSubjectSection.setSectionId(newSection.getId()); 				// Using newly created section id
				newSubjectSection.setStatus(SubjectSection.STATUS_ACTIVE);
				newSubjectSection.setModifiedDate(new Date());
				newSubjectSection.setRefSubjectSectionId(ss.getRefSubjectSectionId());
				
				newSubjectSection = subjectSectionDao.saveEntity(newSubjectSection);
				
				// Getting chapters
				List<Chapter> chapterList = chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);
				chapterOrder=1;
				for(Chapter ch : chapterList)
				{
					// Saving chapter
					Chapter newChapter = new Chapter();
					//newChapter.setNameUTF8(ch.getNameUTF8());
					newChapter.setName(ch.getName());
					newChapter.setRefChapterId(ch.getId());
					newChapter.setDescription((ch.getDescription() != null ? ch.getDescription() : null));
					newChapter.setGradeId(newGrade.getId()); 						// Using newly created grade id
					newChapter.setSubjectId(newSubject.getId());					// Using newly created subject id
					newChapter.setSchoolId(schoolId); 
					newChapter.setBoardId(null);
					newChapter.setSerialOrder(chapterOrder++);
					newChapter.setStatus(Chapter.STATUS_ACTIVE);
					newChapter.setModifiedDate(new Date());
					
					newChapter = chapterDao.saveEntity(newChapter);
					
					// Getting topics
					List<Topic> topicList = topicDao.getTopics(String.valueOf(ch.getId()), Chapter.STATUS_ACTIVE);
					topicOrder=1;
					for(Topic top : topicList)
					{
						Topic newTopic = new Topic();
						newTopic.setRefTopicId(top.getId());
						newTopic.setChapterId(newChapter.getId());					// Using newly created chapter id
						newTopic.setSubjectId(newSubject.getId());					// Using newly created subject id
						newTopic.setBoardId(null); 
						newTopic.setDescription((top.getDescription() != null ? top.getDescription() : null));
						newTopic.setGradeId(newGrade.getId());
						//newTopic.setNameUTF8(top.getNameUTF8());
						newTopic.setName(top.getName());
						newTopic.setSchoolId(schoolId);
						newTopic.setSerialOrder(topicOrder++);
						newTopic.setStatus(Topic.STATUS_ACTIVE);
						newTopic.setModifiedDate(new Date());
						
						newTopic = topicDao.saveEntity(newTopic);
						
						// Getting questionTopic List
						List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, top.getId(), Topic.STATUS_ACTIVE);
						List<LearnObjective> lolist = lODao.getLos(top.getId().toString(), Topic.STATUS_ACTIVE);
						
						for(LearnObjective lolistdata : lolist)
						{ 
							LearnObjective lol = new LearnObjective();
							lol.setRefLearnObjective(lolistdata.getId());
							lol.setBoardId(lolistdata.getBoardId());
							lol.setChapterId(lolistdata.getChapterId());
							lol.setGradeId(lolistdata.getGradeId());
							lol.setName(lolistdata.getName());
							lol.setStatus(lolistdata.getStatus());
							lol.setSubjectId(lolistdata.getSubjectId());
							lol.setTopicId(newTopic.getId());
							lol.setTopicLoCount(lolistdata.getTopicLoCount()); 
							lODao.saveEntity(lol);
							/*newQuestionTopic.setLoId(qt.getLoId());
							newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());*/
							System.out.println("t.getId()old="+newTopic.getId()+"=newTopic.getId()=="+top.getId().toString()+"==lolid=="+lol.getId());
						}
						for(QuestionTopic qt : questionTopicList)
						{
							// Saving QuestionTopic
							QuestionTopic newQuestionTopic = new QuestionTopic();
							newQuestionTopic.setRefQuestiontopicId(qt.getId());
							newQuestionTopic.setModifiedDate(new Date());
							newQuestionTopic.setQuestionId(qt.getQuestionId());
							newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
							newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
							newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
							newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
							newQuestionTopic.setLoId(qt.getLoId());
							newQuestionTopic.setZoneLevelId(qt.getZoneLevelId());
							questionTopicDao.saveEntity(newQuestionTopic);
						}
						
						// Getting moduleTopic List
						List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, top.getId(), ModuleTopic.STATUS_ACTIVE);
						
						for(ModuleTopic mt : moduleTopicList)
						{
							// Saving ModuleTopic
							ModuleTopic newModuleTopic = new ModuleTopic();
							newModuleTopic.setRefModuletopicId(mt.getId());
							newModuleTopic.setModifiedDate(new Date());
							newModuleTopic.setModuleId(mt.getModuleId());
							newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
							newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
							
							moduleTopicDao.saveEntity(newModuleTopic);
						}
					}
				}
				
			}
		}
		
		return null;
	}

	@Override
	@Transactional
	public Integer getMaxOrderChapter(Long subjectId, Long gradeId, Long boardId, Long schoolId)
	{
		return chapterDao.getMaxOrderChapter(subjectId, gradeId, boardId, schoolId);
	}

	@Override
	@Transactional
	public Integer getMaxOrderTopic(Long chapterId)
	{
		return topicDao.getMaxOrderTopic(chapterId);
	}

	@Override
	@Transactional
	public List<ModuleTopic> getModuleTopics(Long moduleId, Long topicId, Integer status)
	{
		return moduleTopicDao.getModuleTopics(null, topicId, status);
	}

	@Override
	@Transactional
	public List<Subject> getDistinctSubjects(Long boardId, Integer status)
	{
		return subjectDao.getDistinctSubjects(boardId, status);
	}

	public String validateExcel(MultipartFile multiPartFile) throws IOException
	{
		if (multiPartFile.getSize() == 0)
			return "noFile";
		
		byte[] fileByte = multiPartFile.getBytes();
		String fileName = multiPartFile.getOriginalFilename();
		InputStream is = new ByteArrayInputStream(fileByte);
		Workbook workbook = null;
		Sheet worksheet = null;
		String message = null;
		int count = 0;
		
		if(fileName.endsWith(".xls"))
			workbook = new HSSFWorkbook(is);
		else if(fileName.endsWith(".xlsx"))
			workbook = new XSSFWorkbook(is);
		else
			return "wrongExtension";
		
		for(int i=0; i<workbook.getNumberOfSheets(); i++)	
		{
			worksheet = workbook.getSheetAt(i);
			
			message = validateSheet(worksheet);
			
			if(message != null && message.equals("noData"))
				count++;
			if(message != null && message.contains("wrongFormat"))		// If any sheets having wrong format
				return message;
			if(message != null && message.contains(","))		// If any sheet having row with empty cell
				return message;
		}
		
		if(count == 15)			// In case there is no data in any sheet
			return "noData";
		
		return null;
	}
	
	public String validateSheet(Sheet worksheet)
	{
		Row row = null;
		Cell cell1 = null;
		Cell cell2 = null;
		Cell cell3 = null;
		Cell cell4 = null;
				
		Iterator<Row> rowIterator = worksheet.iterator();
		
		if(rowIterator.hasNext())			// To skip first line (Header row)
			rowIterator.next();
		
		if(rowIterator.hasNext() == false)
			return "noData";
		else
		{
			while(rowIterator.hasNext())
			{
				row = rowIterator.next();
				
				cell1 = row.getCell(0);
				cell2 = row.getCell(1);
				cell3 = row.getCell(2);
				cell4 = row.getCell(3);
				
				if(cell4 != null && cell4.getCellType() != Cell.CELL_TYPE_BLANK)					// If wrong formatted excel is selected
					return "wrongFormat";
				
				if(cell1 == null || cell1.getCellType() == Cell.CELL_TYPE_BLANK || 
				   cell2 == null || cell2.getCellType() == Cell.CELL_TYPE_BLANK || 
				   cell3 == null || cell3.getCellType() == Cell.CELL_TYPE_BLANK)
					return worksheet.getSheetName()+","+(row.getRowNum()+1);						// Returning sheet name with row no. where blank cell found	// Since row no starts from 0
				
			}
		}
		
		return null;
	}
	
	@Override
	@Transactional(rollbackFor = {IOException.class})		// Making transaction rollback for checked exception.
	public String createSubjectChapterAndTopics(MultipartFile multiPartFile, Long boardId) throws IOException
	{
		String message = validateExcel(multiPartFile);
		if(message != null)
			return message;
			
		byte[] fileByte = multiPartFile.getBytes();
		String fileName = multiPartFile.getOriginalFilename();
		InputStream is = new ByteArrayInputStream(fileByte);
		Workbook workbook = null;
		Sheet worksheet = null;
		
		if(fileName.endsWith(".xls"))
			workbook = new HSSFWorkbook(is);
		if(fileName.endsWith(".xlsx"))
			workbook = new XSSFWorkbook(is);
		
		Long prepGrade = null;
		Long kgGrade = null;
		Long nurseryGrade = null;
		Long firstGrade = null;
		Long secondGrade = null;
		Long thirdGrade = null;
		Long fourthGrade = null;
		Long fifthGrade = null;
		Long sixthGrade = null;
		Long seventhGrade = null;
		Long eighthGrade = null;
		Long ninthGrade = null;
		Long tenthGrade = null;
		Long eleventhGrade = null;
		Long twelfthGrade = null;
		Long prepSection = null;
		Long kgSection = null;
		Long nurserySection = null;
		Long firstSection = null;
		Long secondSection = null;
		Long thirdSection = null;
		Long fourthSection = null;
		Long fifthSection = null;
		Long sixthSection = null;
		Long seventhSection = null;
		Long eighthSection = null;
		Long ninthSection = null;
		Long tenthSection = null;
		Long eleventhSection = null;
		Long twelfthSection = null;
		
		// Getting grade ids of all grades of a particular board
		List<Section> sections = sectionDao.getSections(null, boardId, null, Section.STATUS_ACTIVE, null, "asc");
		for(Section s : sections)
		{
			if(s.getGrade().getName().equalsIgnoreCase("Prep"))
			{
				prepGrade = s.getGrade().getId();
				prepSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("K.G"))
			{
				kgGrade = s.getGrade().getId();
				kgSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Nursery"))
			{
				nurseryGrade = s.getGrade().getId();
				nurserySection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 1"))
			{
				firstGrade = s.getGrade().getId();
				firstSection = s.getId();
				
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 2"))
			{
				secondGrade = s.getGrade().getId();
				secondSection = s.getId();
				
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 3"))
			{
				thirdGrade = s.getGrade().getId();
				thirdSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 4"))
			{
				fourthGrade = s.getGrade().getId();
				fourthSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 5"))
			{
				fifthGrade = s.getGrade().getId();
				fifthSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 6"))
			{
				sixthGrade = s.getGrade().getId();
				sixthSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 7"))
			{
				seventhGrade = s.getGrade().getId();
				seventhSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 8"))
			{
				eighthGrade = s.getGrade().getId();
				eighthSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 9"))
			{
				ninthGrade = s.getGrade().getId();
				ninthSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 10"))
			{
				tenthGrade = s.getGrade().getId();
				tenthSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 11"))
			{
				eleventhGrade = s.getGrade().getId();
				eleventhSection = s.getId();
			}
			if(s.getGrade().getName().equalsIgnoreCase("Grade 12"))
			{
				twelfthGrade = s.getGrade().getId();
				twelfthSection = s.getId();
			}
		}
		
		for(int i=0; i<workbook.getNumberOfSheets(); i++)
		{
			worksheet = workbook.getSheetAt(i);
			
			if(worksheet.getSheetName().trim().equalsIgnoreCase("prep"))
				saveSubjectChapterAndTopics(worksheet, boardId, prepGrade, prepSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("k.g"))
				saveSubjectChapterAndTopics(worksheet, boardId, kgGrade, kgSection);	
			if(worksheet.getSheetName().trim().equalsIgnoreCase("nursery"))
				saveSubjectChapterAndTopics(worksheet, boardId, nurseryGrade, nurserySection);	
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-1"))
				saveSubjectChapterAndTopics(worksheet, boardId, firstGrade, firstSection);	
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-2"))
				saveSubjectChapterAndTopics(worksheet, boardId, secondGrade, secondSection);	
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-3"))
				saveSubjectChapterAndTopics(worksheet, boardId, thirdGrade, thirdSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-4"))
				saveSubjectChapterAndTopics(worksheet, boardId, fourthGrade, fourthSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-5"))
				saveSubjectChapterAndTopics(worksheet, boardId, fifthGrade, fifthSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-6"))
				saveSubjectChapterAndTopics(worksheet, boardId, sixthGrade, sixthSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-7"))
				saveSubjectChapterAndTopics(worksheet, boardId, seventhGrade, seventhSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-8"))
				saveSubjectChapterAndTopics(worksheet, boardId, eighthGrade, eighthSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-9"))
				saveSubjectChapterAndTopics(worksheet, boardId, ninthGrade, ninthSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-10"))
				saveSubjectChapterAndTopics(worksheet, boardId, tenthGrade, tenthSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-11"))
				saveSubjectChapterAndTopics(worksheet, boardId, eleventhGrade, eleventhSection);
			if(worksheet.getSheetName().trim().equalsIgnoreCase("grade-12"))
				saveSubjectChapterAndTopics(worksheet, boardId, twelfthGrade, twelfthSection);
		}
		
		return "success";
	}
	
	public void saveSubjectChapterAndTopics(Sheet worksheet, Long boardId, Long gradeId, Long sectionId)
	{
		Row row = null;
		String subjectNameCheck = null;
		String chapterNameCheck = null;
		String subjectName;
		String chapterName;
		String topicName;
		Long subjectId = null;
		Long chapterId = null;
		List<Subject> subjects;
		Chapter chapter;
		
		Iterator<Row> rowIterator = worksheet.iterator();
		
		// To skip first line (Header row) //
		if(rowIterator.hasNext())			
			rowIterator.next();
		
		while(rowIterator.hasNext())
		{
			row = rowIterator.next();	
			
			row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);		//(Cell.CELL_TYPE_NUMERIC)
			row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
			row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
			
			subjectName = row.getCell(0).getStringCellValue().trim();		// getNumericCellValue()
			chapterName = row.getCell(1).getStringCellValue().trim();
			topicName = row.getCell(2).getStringCellValue().trim();
			
			// Retrieving subject id if subject already exists else save subject and get id
			// Saving extra hits in DB if subject name is same
			if(subjectNameCheck == null) 		// For first time only
			{
				subjects =  subjectDao.getSubjects(boardId, null, subjectName, Subject.STATUS_ACTIVE);
				
				if(subjects.size() > 0)
				{
					subjectNameCheck = subjects.get(0).getName();
					subjectId = subjects.get(0).getId();
				}
				else
				{
					Subject subject = new Subject();
					subject.setName(subjectName);
					subject.setBoardId(boardId);
					subject.setStatus(Subject.STATUS_ACTIVE);
					subject.setModifiedDate(new Date());

					subjectNameCheck = subjectName;
					subjectId = subjectDao.saveEntity(subject).getId();
				}
			}
			else	
			{
				if(!subjectName.equalsIgnoreCase(subjectNameCheck))
				{
					subjects =  subjectDao.getSubjects(boardId, null, subjectName, Subject.STATUS_ACTIVE);
					
					if(subjects.size() > 0)
					{
						subjectNameCheck = subjects.get(0).getName();
						subjectId = subjects.get(0).getId();
					}
					else
					{
						Subject subject = new Subject();
						subject.setName(subjectName);
						subject.setBoardId(boardId);
						subject.setStatus(Subject.STATUS_ACTIVE);
						subject.setModifiedDate(new Date());

						subjectNameCheck = subjectName;
						subjectId = subjectDao.saveEntity(subject).getId();
					}
				}
			}
			
			// Save subject section mapping
			if(subjectSectionDao.checkMappingAvailability(sectionId, subjectId, SubjectSection.STATUS_ACTIVE) == true)		// Checking either mapping already exists or not first
			{
				SubjectSection subjectSection = new SubjectSection();
				subjectSection.setModifiedDate(new Date());
				subjectSection.setSectionId(sectionId);
				subjectSection.setSubjectId(subjectId);
				subjectSection.setStatus(SubjectSection.STATUS_ACTIVE);
				subjectSectionDao.saveEntity(subjectSection);
			}
			
			// Retrieving chapter id if chapter already exists else save chapter and get id
			// Saving extra hits in DB if chapter name is same
			if(chapterNameCheck == null) 		// For first time only
			{
				chapter = chapterDao.getChapter(gradeId, subjectId, Chapter.STATUS_ACTIVE, chapterName);
				
				if(chapter != null)
				{
					chapterNameCheck = chapter.getName();
					chapterId = chapter.getId();
				}
				else
				{
					Chapter ch = new Chapter();
					ch.setName(chapterName);
					ch.setBoardId(boardId); 
					ch.setGradeId(gradeId); 
					ch.setSubjectId(subjectId);
					ch.setSerialOrder(chapterDao.getMaxOrderChapter(subjectId, gradeId, boardId, null));
					ch.setStatus(Chapter.STATUS_ACTIVE);
					ch.setModifiedDate(new Date());
					
					chapterNameCheck = chapterName;
					chapterId = chapterDao.saveEntity(ch).getId();
				}
			}
			else	
			{
				if(!chapterName.equalsIgnoreCase(chapterNameCheck))
				{
					chapter = chapterDao.getChapter(gradeId, subjectId, Chapter.STATUS_ACTIVE, chapterName);
					
					if(chapter != null)
					{
						chapterNameCheck = chapter.getName();
						chapterId = chapter.getId();
					}
					else
					{
						Chapter ch = new Chapter();
						ch.setName(chapterName);
						ch.setBoardId(boardId); 
						ch.setGradeId(gradeId); 
						ch.setSubjectId(subjectId);
						ch.setSerialOrder(chapterDao.getMaxOrderChapter(subjectId, gradeId, boardId, null));
						ch.setStatus(Chapter.STATUS_ACTIVE);
						ch.setModifiedDate(new Date());
						
						chapterNameCheck = chapterName;
						chapterId = chapterDao.saveEntity(ch).getId();
					}
				}
			}
			
			// Checking topic availability if doesn't exist then persist
			if(topicDao.checkTopicAvailability(chapterId, Chapter.STATUS_ACTIVE, topicName) == true)
			{
				Topic top = new Topic();
				top.setName(topicName);
				top.setBoardId(boardId);
				top.setGradeId(gradeId);
				top.setChapterId(chapterId); 
				top.setSubjectId(subjectId); 
				top.setSerialOrder(topicDao.getMaxOrderTopic(chapterId)); 
				top.setStatus(Topic.STATUS_ACTIVE); 
				top.setModifiedDate(new Date());
				topicDao.saveEntity(top);
			}
		}
	}

	@Override
	@Transactional
	public void updateChapterOrder(String[] idList, String[] serialOrderList)
	{
		for(int j = 0; j < idList.length; j++)
		{
			chapterDao.updateChapterOrder(Long.valueOf(idList[j]), Integer.valueOf(serialOrderList[j]));
		}
	}

	@Override
	@Transactional
	public String savePrice(HttpServletRequest request)
	{
		String boardId = request.getParameter("boardId");
		String gradeId = request.getParameter("gradeId");
		String subjectId = request.getParameter("subjectId");
		String price1 = request.getParameter("price1");
		String price2 = request.getParameter("price2");
		String price3 = request.getParameter("price3");

		return adminDao.savePrice(boardId, gradeId, subjectId, price1, price2, price3);
	}

	@Override
	@Transactional
	public void updateTopicOrder(String[] idList, String[] serialOrderList)
	{
		for(int j = 0; j < idList.length; j++)
		{
			topicDao.updateTopicOrder(Long.valueOf(idList[j]), Integer.valueOf(serialOrderList[j]));
		}
	}

	@Transactional
	@Override
	public String updateSubject(HttpServletRequest request, long userId, String swName)
	{
	String result= subjectDao.updateSubject(request, userId,swName);
	if(result.equals("success"))
	{
		UpdateLog updateLog=new UpdateLog();
		updateLog.setDate(new Date());
		updateLog.setEntityType(EntityType.Subject);
		updateLog.setEntityId(Long.parseLong(request.getParameter("txtSubjectId")));
		updateLog.setOldEntityName(request.getParameter("txtOldSubject"));
		updateLog.setUpdatedName(request.getParameter("txtNewSubject"));;
		updateLog.setUserid(userId);
		adminDao.insertUpdateLog(updateLog);
		
	}
	return result;
	}

	@Override
	public String insertUpdateLog(UpdateLog updateLog)
	{
		return adminDao.insertUpdateLog(updateLog);
	}
	@Transactional
	@Override
	public String updateTopic(HttpServletRequest request, long id)
	{
	String result= topicDao.updateTopic(request, id);
	if(result.equals("success"))
	{
		UpdateLog updateLog=new UpdateLog();
		updateLog.setDate(new Date());
		updateLog.setEntityType(EntityType.Topic);
		updateLog.setEntityId(Long.parseLong(request.getParameter("id")));
		updateLog.setOldEntityName(request.getParameter("oldName"));
		updateLog.setUpdatedName(request.getParameter("newName"));;
		updateLog.setUserid(id);
		adminDao.insertUpdateLog(updateLog);
		
	}
	return result;
	}
	@Transactional
	@Override
	public String updateChapter(HttpServletRequest request, long id)
	{
		String result=chapterDao.updateChapter(request, id);
	
		if(result.equals("success"))
		{
			UpdateLog updateLog=new UpdateLog();
			updateLog.setDate(new Date());
			updateLog.setEntityType(EntityType.Chapter);
			updateLog.setEntityId(Long.parseLong(request.getParameter("id")));
			updateLog.setOldEntityName(request.getParameter("oldName"));
			updateLog.setUpdatedName(request.getParameter("newName"));;
			updateLog.setUserid(id);
			adminDao.insertUpdateLog(updateLog);
			
		}
		return result;
	}
	@Transactional
	@Override
	public String updateGrade(HttpServletRequest request, long id)
	{
		String result=gradeDao.updateGrade(request, id);
		
		if(result.equals("success"))
		{
			UpdateLog updateLog=new UpdateLog();
			updateLog.setDate(new Date());
			updateLog.setEntityType(EntityType.Grade);
			updateLog.setEntityId(Long.parseLong(request.getParameter("id")));
			updateLog.setOldEntityName(request.getParameter("oldName"));
			updateLog.setUpdatedName(request.getParameter("newName"));;
			updateLog.setUserid(id);
			adminDao.insertUpdateLog(updateLog);
			
		}
		return result;
	}

	@Transactional
	@Override
	public String updateBoard(HttpServletRequest request, long id)
	{
		String result=gradeDao.updateBoard(request, id);
		
		if(result.equals("success"))
		{
			UpdateLog updateLog=new UpdateLog();
			updateLog.setDate(new Date());
			updateLog.setEntityType(EntityType.Board);
			updateLog.setEntityId(Long.parseLong(request.getParameter("id")));
			updateLog.setOldEntityName(request.getParameter("oldName"));
			updateLog.setUpdatedName(request.getParameter("newName"));;
			updateLog.setUserid(id);
			adminDao.insertUpdateLog(updateLog);
			
		}
		return result;
	}

	@Override
	@Transactional
	public List<QuestionComments> getCommentsnotificationsWithLimit(Integer limit)
	{
		return adminDao.getCommentsnotificationsWithLimit(limit);
	}

	@Override
	@Transactional
	public Question getQuestionDetailsById(Long questionId)
	{
		return adminDao.getQuestionDetailsById(questionId);
	}

	@Override
	@Transactional
	public List<QuestionComments> getAllCommentsByQuestionId(Long questionId)
	{
		return adminDao.getAllCommentsByQuestionId(questionId);
	}

	@Override
	@Transactional
	public void updateNotificationCountStatus()
	{
		adminDao.updateNotificationCountStatus();
		
	}

	@Transactional
	@Override
	public List<QuestionTopic> getQuestions(String topicIdMap, Integer status)
	{
			return questionDao.getQuestions(topicIdMap, status);
		
	}
	@Transactional
	@Override
	public String MapQuestionWithTopic(Long QmapWithTopic, String allQuestionsChecked[])
	{
		String sql = "select questionId from QuestionTopic where topicId=:topicId";
		Query query = sessionFactory.getCurrentSession().createQuery(sql);
		query.setParameter("topicId", QmapWithTopic);
		// query.setMaxResults(1);
		List<Long> existingQuestionList = query.list();
		for (int i = 0; i < allQuestionsChecked.length; i++)
		{
			
			 
			
			String[] data=allQuestionsChecked[i].split("\\@");
			if (!existingQuestionList.contains(Long.parseLong(data[0])))
			{
				
				QuestionTopic newQuestionTopic = new QuestionTopic();
				newQuestionTopic.setModifiedDate(new Date());
				newQuestionTopic.setQuestionId(Long.parseLong(data[0]));
				newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
				newQuestionTopic.setTopicId(QmapWithTopic); 				// Using newly created topic id
				newQuestionTopic.setDifficultyLevelId(Long.parseLong(data[2]));	
				newQuestionTopic.setCognitiveLevelId(Long.parseLong(data[1]));
				questionTopicDao.saveEntity(newQuestionTopic);
				System.out.println(allQuestionsChecked[i]+"================Mapped");
			}
			else
			{
				System.out.println(allQuestionsChecked[i]+"================avilable");
			}

		}

		return "success";

	}

	@Transactional
	@Override
	public List<QuestionTopic> getSubjectWiseQuestions(Long subjectId, Long gradeId)
	{

		return chapterDao.getSubjectWiseQuestions(subjectId,gradeId);
	}

	@Override
	@Transactional
	public Boolean enableDisableQuestion(Long questionId, Integer status)
	{
		return adminDao.enableDisableQuestion(questionId,status);
	}

	@Override
	@Transactional
	public List<Chapter> getAllEnableDisableChapters(String subjectIdString, Long gradeId)
	{
		return chapterDao.getAllEnableDisableChapters(subjectIdString,gradeId);
	}

	@Override
	@Transactional
	public Boolean enableDisableChapter(Long chapId, Integer status)
	{
		return chapterDao.enableDisableChapter(chapId, status);
	}

	@Override
	@Transactional
	public Boolean enableDisableTopic(Long topicId, Integer status)
	{
		return topicDao.enableDisableTopic(topicId,status);
	}

	@Override
	@Transactional
	public List<Topic> getAllEnableDisableTopics(Long chapterId, Integer status)
	{
		return topicDao.getAllEnableDisableTopics(chapterId,status);
	}

	@Override
	@Transactional
	public Boolean checkQuestionIsAssignedOrNot(Long questionId)
	{
		return adminDao.getAllEnableDisableTopics(questionId);
	}

	@Override
	@Transactional
	public List<Grade> syncingStatus(Long boardId,Long gradeId)
	{
		
		return adminDao.syncingStatus(boardId,gradeId);
		
	}

	@Override
	@Transactional
	public void syningUpdate(Long sectionIdSource, Long boardIdTarget, Long gradeIdTarget, Long sectionIdTarget)
	{
		 adminDao.syningUpdate(sectionIdSource, boardIdTarget, gradeIdTarget, sectionIdTarget);
		
	}

	@Override
	@Transactional
	public void addGradeAndSectionMappingExistedMerge(Long sectionIdSource, Long boardIdTarget, Long gradeIdTarget, Long sectionIdTarget)
	{
		
		
		// Getting SubjectSection mapping
		List<SubjectSection> subjectSectionList = subjectSectionDao.getSubjectSections(String.valueOf(sectionIdSource), SubjectSection.STATUS_ACTIVE);
		int topicOrder,chapterOrder;
		for(SubjectSection ss : subjectSectionList)
		{
			// Saving subject
			Subject newSubject = new Subject();
			newSubject.setBoardId(boardIdTarget);
			newSubject.setSchoolId(null);
			newSubject.setModifiedDate(new Date());
			newSubject.setName(ss.getSubject().getName());
			newSubject.setStatus(Subject.STATUS_ACTIVE);
			if(ss.getSubject().getSubImage()!=null){
				newSubject.setSubImage(ss.getSubject().getSubImage());
			}
			newSubject = (Subject)subjectDao.mergeEntity(newSubject);
			
			
			// Saving subjectSection mapping
			SubjectSection newSubjectSection = new SubjectSection();
			newSubjectSection.setModifiedDate(new Date());
			newSubjectSection.setSectionId(sectionIdTarget);			
			newSubjectSection.setSubjectId(newSubject.getId());						// Using newly created subject id
			newSubjectSection.setStatus(SubjectSection.STATUS_ACTIVE);
			
			newSubjectSection = (SubjectSection)subjectSectionDao.mergeEntity(newSubjectSection);
			
			// Getting Chapter List
			List<Chapter> chapterList =  chapterDao.getChapters(String.valueOf(ss.getSubjectId()), ss.getSection().getGradeId(), Chapter.STATUS_ACTIVE);		
			chapterOrder=1;
			for(Chapter c : chapterList)
			{
				// Saving chapter
				Chapter newChapter = new Chapter();
				//newChapter.setNameUTF8(c.getNameUTF8());
				newChapter.setName(c.getName());
				newChapter.setGradeId(gradeIdTarget); 						
				newChapter.setSubjectId(newSubject.getId()); 						// Using newly created subject id
				newChapter.setBoardId(boardIdTarget);
				newChapter.setSchoolId(null);
				newChapter.setModifiedDate(new Date());
				newChapter.setSerialOrder(chapterOrder++);
				newChapter.setStatus(Chapter.STATUS_ACTIVE);
				
				newChapter = (Chapter)chapterDao.mergeEntity(newChapter);
				
				// Getting Topic List
				List<Topic> topicList = topicDao.getTopics(String.valueOf(c.getId()), Topic.STATUS_ACTIVE);
				topicOrder=1;
				for(Topic t : topicList)
				{
					// Saving topic
					Topic newTopic = new Topic();
					//newTopic.setNameUTF8(t.getNameUTF8());
					newTopic.setName(t.getName());
					newTopic.setGradeId(gradeIdTarget);
					newTopic.setChapterId(newChapter.getId());						// Using newly created chapter id
					newTopic.setSubjectId(newSubject.getId()); 						// Using newly created subject id
					newTopic.setBoardId(boardIdTarget);		
					newTopic.setSchoolId(null);
					newTopic.setModifiedDate(new Date());
					newTopic.setSerialOrder(topicOrder++);
					newTopic.setStatus(Topic.STATUS_ACTIVE);
					
					newTopic = (Topic)topicDao.mergeEntity(newTopic);
					
					// Getting questionTopic List
					List<QuestionTopic> questionTopicList = questionTopicDao.getQuestionTopics(null, t.getId(), Topic.STATUS_ACTIVE);
					
					for(QuestionTopic qt : questionTopicList)
					{
						// Saving QuestionTopic
						QuestionTopic newQuestionTopic = new QuestionTopic();
						newQuestionTopic.setModifiedDate(new Date());
						newQuestionTopic.setQuestionId(qt.getQuestionId());
						newQuestionTopic.setStatus(QuestionTopic.STATUS_ACTIVE);
						newQuestionTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
						newQuestionTopic.setCognitiveLevelId(qt.getCognitiveLevelId());
						newQuestionTopic.setDifficultyLevelId(qt.getDifficultyLevelId());
						questionTopicDao.mergeEntity(newQuestionTopic);
					}
					
					// Getting moduleTopic List
					List<ModuleTopic> moduleTopicList = moduleTopicDao.getModuleTopics(null, t.getId(), ModuleTopic.STATUS_ACTIVE);
					
					for(ModuleTopic mt : moduleTopicList)
					{
						// Saving ModuleTopic
						ModuleTopic newModuleTopic = new ModuleTopic();
						newModuleTopic.setModifiedDate(new Date());
						newModuleTopic.setModuleId(mt.getModuleId());
						newModuleTopic.setTopicId(newTopic.getId()); 				// Using newly created topic id
						newModuleTopic.setStatus(ModuleTopic.STATUS_ACTIVE);
						
						moduleTopicDao.mergeEntity(newModuleTopic);
						
					}
				}
			}
		}
		System.out.println("--------------------------------------SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS________________-----------------__________---------_____");
		
	//	return "success";
		
		
		//adminDao.addGradeAndSectionMappingExistedMerge(sectionIdSource, boardIdTarget, gradeIdTarget, sectionIdTarget);
		
	}
	/*
	@Override
	@Transactional
	public ZoneCreate addBoard2(ZoneCreate board)
	{
		
		return CreateDao.saveOrUpdateEntity2(board);
	}*/

	 
	@Override
	@Transactional
	public List<TopicGraph> getTopicGraph(String topicIdString, String userId)
	{
		// TODO Auto-generated method stub
		return lODao.getTopicGraph(topicIdString, userId);
	//	return null;
	}

	



}