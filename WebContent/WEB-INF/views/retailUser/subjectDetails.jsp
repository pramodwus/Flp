<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="../student/studentHeader.jsp" /> 

<!-- middle panel -->
<div class="container-fluid bred-cont">
<div class="container">
<div class="row">
<ul class="breadcrumb">
<c:forEach var="sub" items="${sessionScope.user.subjects }" varStatus="status">

<c:if test="${subjectId eq sub.id}">
<li>Browse ${sub.name }<input type="hidden" id="hiddenSubjectName" value="${sub.name }"/></li>
</c:if>

</c:forEach>
</ul>
</div>
</div>
</div>


<div class="container-fluid mrt20 question-con">
<div class="container">
<div class="row">
<div class="col-md-9 col-sm-8 col-xs-12">
<div class="row">
<div class="panel-group" id="accordion">


<c:if test="${not empty chapterList }">
<a  href="#" data-toggle="modal" data-target="#myModal" style="display: none;" id="hiddenTopicId"></a>
<c:set var="count" value="0"/>
							<c:forEach var="chapters" items="${chapterList }"
								varStatus="status">

								<div class="panel panel-default panel-cont accordion-toggle"
									data-toggle="collapse" data-parent="#accordion"
									href="#collapse${status.count }">

									<c:if test="${(activeChapter eq chapters.id) || (activeChapter eq 0 && count eq 0 )}">
										<div class="panel-heading panel-head-active">
										
									</c:if>
									<c:if test="${(activeChapter eq 0 && status.count ne 1) || (activeChapter ne chapters.id && activeChapter ne 0)  }">
									<div class="panel-heading">
									</c:if>
									
									
									<h4 class="panel-title">
										<b class="mrr10 pull-left"><img
											src="${pageContext.request.contextPath}/resources/images/arrow.png"
											class="arrowCls" /></b>
										<div class="topic-question-name">${chapters.name }</div>
										<span class="glyphicon glyphicon-plus pull-right mrl10"></span>
										<div class="pro-bar-cont pull-right">
											<p class="percentCls">${chapters.resultPercent}% Completed</p>
											<div class="progress">
												<div class="progress-bar" role="progressbar"
													aria-valuenow="70" aria-valuemin="0" aria-valuemax="100"
													style="width: ${chapters.resultPercent}%"></div>
											</div>
										</div>
									</h4>
								</div>
									<c:if test="${(activeChapter eq 0 && count eq 0 ) || (activeChapter eq chapters.id )}">
									<c:set var="count" value="${count+1 }"/>
									<div id="collapse${status.count }" class="panel-collapse collapse in">
								</c:if>
								<c:if test="${(activeChapter eq 0 && status.count ne 1) || (activeChapter ne chapters.id && activeChapter ne 0)  }">
									<div id="collapse${status.count }" class="panel-collapse collapse">
								</c:if>
								<div class="panel-body">

									<ul class="list-unstyled discussion">
										
										<c:forEach var="topic" items="${chapters.topicSet }" varStatus="status1">
											<li ><b
												class="glyphicon glyphicon-chevron-right mrr10"></b><a href="javascript:void(0)" class="topicCls" id="${topic.id }" dir="${chapters.id }">${topic.name }</a>
												<span style="float: right;margin-left: 10px;" id="favTopicId${status.count }${status1.count }">
												<c:choose><c:when test="${topic.isfavorite eq true }"><img src="${pageContext.request.contextPath}/resources/images/st1.png" width="30px;" title="Favorites"/></c:when>
												<c:otherwise><a href="javascript:void(0)"  onclick="setFavouriteTopic(${subjectId},${chapters.id },${topic.id},${status.count }${status1.count })"><img src="${pageContext.request.contextPath}/resources/images/st2.png" width="30px;" title="Favorites"/></a></c:otherwise>
												</c:choose>
												</span>
												<div id="diagram-id${status.count }${status1.count }" class="diagram pull-right"></div></li>
												<c:if test="${topic.percent le 30 }">
												<script type="text/javascript">
												$('#diagram-id${status.count}${status1.count}').circleDiagram({
													   "percent": "${topic.percent}%",
														"size": "50",
														"borderWidth": "4",
														"bgFill": "#e2e2e2",
														"frFill": "red",
														"textSize": "12",
														"textColor": "red"
													});

												</script>
												</c:if>
												<c:if test="${topic.percent gt 30 && topic.percent le 70}">
												<script type="text/javascript">
												$('#diagram-id${status.count}${status1.count}').circleDiagram({
													   "percent": "${topic.percent}%",
														"size": "50",
														"borderWidth": "4",
														"bgFill": "#e2e2e2",
														"frFill": "orange",
														"textSize": "12",
														"textColor": "orange"
													});

												</script>
												</c:if>
												<c:if test="${topic.percent gt 70}">
												<script type="text/javascript">
												$('#diagram-id${status.count}${status1.count}').circleDiagram({
													   "percent": "${topic.percent}%",
														"size": "50",
														"borderWidth": "4",
														"bgFill": "#e2e2e2",
														"frFill": "#289e25",
														"textSize": "12",
														"textColor": "#289e25"
													});

												</script>
												</c:if>
										</c:forEach>
										
									</ul>



								</div></div>
  </div>
  
</c:forEach>

</c:if>


</div>
</div>
</div>

<div class="col-md-3 col-xs-12 col-sm-4 ">
<div class="col-md-12 hidden-xs sub-side-cont">
<div class="row">
<div class="side-head"><img src="${pageContext.request.contextPath}/resources/images/sub-icon.png" alt=""/> Choose your Subjects</div>
<ul class="list-unstyled">

<c:if test="${not empty sessionScope.user.subjects }">
<c:forEach var="sub" items="${sessionScope.user.subjects }" varStatus="status">

<c:if test="${subjectId eq sub.id}">
<li><a href="${pageContext.request.contextPath}/user/subjectDetail.htm?sid=${sub.id}" class="sub-side-cont-active"><b class="glyphicon glyphicon-book mrr10"></b>${sub.name }</a></li>
</c:if>
<c:if test="${subjectId ne sub.id}">
<li><a href="${pageContext.request.contextPath}/user/subjectDetail.htm?sid=${sub.id}"><b class="glyphicon glyphicon-book mrr10"></b>${sub.name }</a></li>
</c:if>
</c:forEach>

</c:if>

</ul>
</div>
</div>
</div>

</div>
</div>
</div>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <div class="modal-title"><span>Your Performance</span> on the Topic</div>
      </div>
      <div class="modal-body">
        <table width="90%" border="0" cellspacing="0" cellpadding="0" class="topic-per table table-bordered table-hover table-condensed">            
  <tr>
    <td><strong>Topic Name:</strong></td>
    <td><span id="topicNameId"></span></td>
  </tr>
  <tr>
    <td><strong>Correct:</strong></td>
    <td><span id="correctCount"></span></td>
  </tr>
  <tr>
    <td><strong>Incorrect:</strong></td>
    <td><span id="incorrectCount"></span></td>
  </tr>
  <tr>
    <td><strong>GenScore:</strong></td>
    <td><span id="jScoreId"></span></td>
  </tr>
</table>

 <div class="btn btn-primary btn-md"><a href="javascript:void(0)" id="continuePraticeId"></a></div> 
      </div>
      <div class="modal-footer">
        <p id=contentId></p>
      </div>
    </div>

  </div>
</div>
<script type="text/javascript">

function setFavouriteTopic(subjectId,chapterId,topicId,rowId){
	 $.post("${pageContext.request.contextPath}/ajax/rest/addFavouriteTopic?subId=" + subjectId+"&chapId="+chapterId+"&topicId="+topicId, function(data)
			
			 {
		 if(data==true || data=='true'){
			 $("#favTopicId"+rowId).html('<img src="${pageContext.request.contextPath}/resources/images/st1.png" width="30px;" title="Favorites"/>');
		 }
		 
			    }); 
}

$(document).ready(function(){
	$(".topicCls").on("click", function(e){
		e.preventDefault();
		e.stopPropagation();
		var topicId=$(this).attr("id");
		var chapterId=$(this).attr('dir');
		var topicName=$(this).html();
		$("#topicNameId").html(topicName);
		 $.post("${pageContext.request.contextPath}/ajax/rest/getCorrectIncorrectQuestionCount/" + topicId, function(data)
				    {
			 var value=data.split(",");
			 $("#correctCount").html(value[0]);
			 $("#incorrectCount").html(value[1]);
			 var genScore=parseInt(value[2]);
			 $("#jScoreId").html(genScore);
			
			 if(genScore==0){
				 $("#continuePraticeId").html('Start Learning');
				 $("#contentId").html('You seem first time coming on this topic, click on the Start Learning button and start learning through assessment method and obtain GenScore at-least 40!');
			 }else if(genScore > 0 && genScore < 40){
				 $("#continuePraticeId").html('Keep Learning');
				 $("#contentId").html('You have performed average on the topic, please attempt few more questions to reach at GenScore 40!');
			 }else if(genScore >= 40 && genScore<100){
				 $("#continuePraticeId").html('Continue Practice');
				 $("#contentId").html('You have passed the learn level and now you will attempt the Practice mode questions that will be little difficult questions to reach the GenScore 100. All the best !!!');
			 }else if(genScore >=100){
				 $("#continuePraticeId").html('Take the Challenge');
				 $("#contentId").html('You have passed the Practice level and now you will attempt the Challenge mode questions that will be difficult questions to reach the GenScore 100+, All the best !!!');
			 }
				    });
		 $("#continuePraticeId").attr('href','${pageContext.request.contextPath}/student/getQuestionAssessmentPage.htm?topicId='+topicId+'&chapterId='+chapterId+'&sId=${param.sid}&subjectName='+$("#hiddenSubjectName").val()+'&topicName='+$("#topicNameId").html());
		$("#hiddenTopicId").trigger("click");
	});
});


</script>

<!-- jQuery -->
<!-- middle panel end -->  
<jsp:include page="studentFooter.jsp" /> 

