<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<style>
.eng-img {
	position: absolute;
	display: none;
	margin: 5% 30%;
	z-index: 999999;
	background: #fff;
	padding: 7px;
	text-align: center;
}
</style>
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=TeX-AMS-MML_HTMLorMML">
</script>
<script>


function getSelectedBoardID1(){
	 //var cbseId=parseInt($("#boardId").val());
	var val='<option value="">Select Board To Map From...</option>';
	<c:forEach var='board' items="${boardList}">
	var id='${board.id}';
	val=val+'<option value="${board.id}">${board.name}</option>';
	/*  if(parseInt(id) !=cbseId){
		val=val+'<option value="${board.id}">${board.name}</option>';
	}else{val=val+'<option value="${board.id}">${board.name}</option>';}  */
</c:forEach>
$("#boardIdMap").empty().append(val);
}

function setpath(p)
{
	alert(p);
	
	}

 

	var addEditRemove;
    
    $(document).ready(function()
    {
	    //$('#reportTableID').dataTable().fnDestroy();			// Use it when you want to reinitialize data table
	    $('#dataTableId').dataTable( {
	        stateSave: true
	    } );
	   

	    $('#questionFormId').ajaxForm({ 
	        beforeSubmit : function(arr, $form, options){
	        /* 	console.log(arr)
	        	console.log(options)
	        */     var fileDataIndex = -1;

	            $.each(arr, function(index, value) {
	            	try {
	            		//console.log("index: "+ index+", value: "+ value.type)
		                 if (value.type == "file"){
		                     if (value.value.length == 0){
		                         fileDataIndex = index;
		                         arr.splice(index,1);
		                     }
		                 }
					} catch (e) {
						//console.log("Error: "+ e)
					}
	            	
	               });

	          /*   if (fileDataIndex != -1){
	               arr.remove(fileDataIndex);
	            }
	          */   
	         }  ,
	         success : function(data){

			        $("#fullPageloader").hide();
			        if(data == "success")
			        {
				        $(".close").trigger('click'); // To hide the pop-up background
				        
				        if(addEditRemove == 'add')
					        alert("Question uploaded successfully");
				        if(addEditRemove == 'edit')
					        alert("Question edited successfully");
				        if(addEditRemove == 'remove')
					        alert("Question removed successfully");
				        
				        getQuestionData(); // Reload question data table
			        }
	          }, 
	          error: function(data){alert("here is error: "+ data);
	          }
	  });  
	    
    });
    
    $(".enlarge").on("mouseover", function()
    {
	    var val = $(this).children().attr('src');
	    $("#engImg").append('<img src="'+val+'"/>').show();
    });
    
    $(".enlarge").mouseleave(function()
    {
	    $("#engImg").empty().hide();
    });
    
    function editQuestion(questionId)
    {
    	 var count=0;
	    addEditRemove = "edit";
	    $(".enlarge").empty()
	    $(".errorCls2").html('');
	    <c:forEach var="quesTopic" items="${questionTopic}"	varStatus="status">;
	    if('${quesTopic.question.id}' == questionId)
	    {
		    $('#editQuestionId').val("${quesTopic.question.id}");
		    $('#editQuestionTopicId').val("${quesTopic.id}");
		    $('#questionTypeId option[value=${quesTopic.question.questionTypeId}]').prop('selected', true);
		    $('#questionDifficultyLevelId option[value=${quesTopic.difficultyLevelId}]').prop('selected', true);
		    $('#questionCognitiveLevelId option[value=${quesTopic.cognitiveLevelId}]').prop('selected', true); 
		    
		    $('#hint_type option[value=${quesTopic.question.hinttypeid}]').prop('selected', true);
		    $('#explanation_type option[value=${quesTopic.question.explanationId}]').prop('selected', true);
		    
		     
		    $('#quesDescription').html("<c:out value='${quesTopic.question.description}' />");
		    
		    <c:if test="${not empty quesTopic.question.imagePath}">;
		    $('#quesImageDivId').empty().append("<img width='70px' height='70px' src='${globalVO.questionResourceDirectory}${quesTopic.question.imagePath}' />").show();
		    </c:if>;
		    
		    <c:forEach var="ans" items="${quesTopic.question.answers}" varStatus="status">
		    <c:if test="${status.count == 1}">;
		    $('#editOptionId1').val("${ans.id}");
		    $('#optionDescription1').html("<c:out value='${ans.description}' />");
		    $("input[name=isCorrectOption1][value=${ans.isCorrect}]").prop('checked', true);
		    
		    <c:if test="${not empty ans.imagePath}">;
		    $('#ansImageDivId1').empty().append("<img width='70px' height='70px' src='${globalVO.questionResourceDirectory}${ans.imagePath}' />").show();
		    </c:if>
		    count++;
		    </c:if>
		    <c:if test="${status.count == 2}">;
		    $('#editOptionId2').val("${ans.id}");
		    $('#optionDescription2').html("<c:out value='${ans.description}' />");
		    $("input[name=isCorrectOption2][value=${ans.isCorrect}]").prop('checked', true);
		    
		    <c:if test="${not empty ans.imagePath}">;
		    $('#ansImageDivId2').empty().append("<img width='70px' height='70px' src='${globalVO.questionResourceDirectory}${ans.imagePath}' />").show();
		    </c:if>
		    count++;
		    </c:if>
		    <c:if test="${status.count == 3}">;
		    $('#editOptionId3').val("${ans.id}");
		    $('#optionDescription3').html("<c:out value='${ans.description}' />");
		    $("input[name=isCorrectOption3][value=${ans.isCorrect}]").prop('checked', true);
		    
		    <c:if test="${not empty ans.imagePath}">;
		    $('#ansImageDivId3').empty().append("<img width='70px' height='70px' src='${globalVO.questionResourceDirectory}${ans.imagePath}' />").show();
		    </c:if>
		    count++;
		    </c:if>
		    <c:if test="${status.count == 4}">;
		    $('#editOptionId4').val("${ans.id}");
		    $('#optionDescription4').html("<c:out value='${ans.description}' />");
		    $("input[name=isCorrectOption4][value=${ans.isCorrect}]").prop('checked', true);
		    
		    <c:if test="${not empty ans.imagePath}">;
		    $('#ansImageDivId4').empty().append("<img width='70px' height='70px' src='${globalVO.questionResourceDirectory}${ans.imagePath}' />");
		    </c:if>
		    count++;
		    </c:if>
		    </c:forEach>;
	    }
	    </c:forEach>;
	    if(count<4)
		{
		$('#editOptionId4').val("");
		$('#optionDescription4').html("");
		
		$("isCorrectOption4").prop('checked', false);;
		$('#ansImageDivId4').empty();
		if(count<3){
			$('#editOptionId3').val("");
			$('#optionDescription3').html("");
			$("isCorrectOption3").prop('checked', false);;
			$('#ansImageDivId3').empty();
			if(count<2){
				$('#editOptionId2').val("");
				$('#optionDescription2').html("");
				$("isCorrectOption2").prop('checked', false);
				$('#ansImageDivId2').empty();
			}
		}
		
		
		}
	    
	    /* 	
	    	<c:forEach var="quesTopic" items="${questionTopic}"	varStatus="status">;
	    		if('${quesTopic.question.id}' == questionId)
	    		{
	    			$('#editQuestionId').val("${quesTopic.question.id}");
	    			$('#editQuestionTopicId').val("${quesTopic.id}");
	    			$('#questionTypeId option[value=${quesTopic.question.questionTypeId}]').prop('selected', true);
	    			$('#questionDifficultyLevelId option[value=${quesTopic.question.difficultyLevelId}]').prop('selected', true);
	    			$('#questionCognitiveLevelId option[value=${quesTopic.question.cognitiveLevelId}]').prop('selected', true);
	    			$('#quesDescription').html("<c:out value='${quesTopic.question.description}' />");
	    			
	    			<c:if test="${not empty quesTopic.question.imagePath}">
	    				$('#quesImageDivId').empty().append("<img style='width:100px;height:100px' src='${globalVO.questionResourceDirectory}${quesTopic.question.imagePath}' />").show();
	    			</c:if>
	    			
	    			<c:forEach var="ans" items="${quesTopic.question.answers}" varStatus="status">
	    				<c:if test="${status.count == 1}">;
	    					$('#editOptionId1').val("${ans.id}");
	    					$('#optionDescription1').html("<c:out value='${ans.description}' />");
	    					$("input[name=isCorrectOption1][value=${ans.isCorrect}]").prop('checked', true);
	    					
	    					<c:if test="${not empty ans.imagePath}">
	    						$('#ansImageDivId1').empty().append("<img style='width:100px;height:100px' src='${globalVO.questionResourceDirectory}${ans.imagePath}' />").show();
	    					</c:if>
	    				</c:if>
	    				<c:if test="${status.count == 2}">;
	    					$('#editOptionId2').val("${ans.id}");
	    					$('#optionDescription2').html("<c:out value='${ans.description}' />");
	    					$("input[name=isCorrectOption2][value=${ans.isCorrect}]").prop('checked', true);
	    					
	    					<c:if test="${not empty ans.imagePath}">
	    						$('#ansImageDivId2').empty().append("<img style='width:100px;height:100px' src='${globalVO.questionResourceDirectory}${ans.imagePath}' />").show();
	    					</c:if>
	    				</c:if>
	    				<c:if test="${status.count == 3}">;
	    					$('#editOptionId3').val("${ans.id}");
	    					$('#optionDescription3').html("<c:out value='${ans.description}' />");
	    					$("input[name=isCorrectOption3][value=${ans.isCorrect}]").prop('checked', true);
	    					
	    					<c:if test="${not empty ans.imagePath}">
	    						$('#ansImageDivId3').empty().append("<img style='width:100px;height:100px' src='${globalVO.questionResourceDirectory}${ans.imagePath}' />").show();
	    					</c:if>
	    				</c:if>
	    				<c:if test="${status.count == 4}">;
	    					$('#editOptionId4').val("${ans.id}");
	    					$('#optionDescription4').html("<c:out value='${ans.description}' />");
	    					$("input[name=isCorrectOption4][value=${ans.isCorrect}]").prop('checked', true);
	    					
	    					<c:if test="${not empty ans.imagePath}">
	    						$('#ansImageDivId4').empty().append("<img style='width:100px;height:100px' src='${globalVO.questionResourceDirectory}${ans.imagePath}' />").show();
	    					</c:if>
	    				</c:if>
	    			</c:forEach>;
	    		}
	    	</c:forEach>; */

	    $('#headerId').empty().html("Edit");
	    	
	    	
	    	selecthint();  selectexplanation();
    }

    function addQuestion()
    {
	    addEditRemove = "add";
	    
	    $('#editQuestionId').val("");
	    $('#editQuestionTopicId').val("");
	    $('#questionTypeId option[value=""]').prop('selected', true);
	    $('#questionDifficultyLevelId option[value=""]').prop('selected', true);
	    $('#questionCognitiveLevelId option[value=""]').prop('selected', true);
	    $('#quesDescription').empty();
	    $('#editOptionId1').val("");
	    $('#editOptionId2').val("");
	    $('#editOptionId3').val("");
	    $('#editOptionId4').val("");
	    $('#optionDescription1').empty();
	    $('#optionDescription2').empty();
	    $('#optionDescription3').empty();
	    $('#optionDescription4').empty();
	    $('#questionImgId').val("");
	    $('#optionImgId1').val("");
	    $('#optionImgId2').val("");
	    $('#optionImgId3').val("");
	    $('#optionImgId4').val("");
	    $('#quesImageDivId').empty().hide();
	    $('#ansImageDivId1').empty().hide();
	    $('#ansImageDivId2').empty().hide();
	    $('#ansImageDivId3').empty().hide();
	    $('#ansImageDivId4').empty().hide();
	    $("input[name=isCorrectOption1][value=0]").prop('checked', true);
	    $("input[name=isCorrectOption2][value=0]").prop('checked', true);
	    $("input[name=isCorrectOption3][value=0]").prop('checked', true);
	    $("input[name=isCorrectOption4][value=0]").prop('checked', true);
	    
	    $('#headerId').empty().html("Add");
    }

   /*  function removeQuestion(questionTopicId)
    {
    	alert(questionTopicId);
	     if(confirm("Are you sure to delete this question?") == false)
		    return false;
	    
	    addEditRemove = "remove";
	    
	    $('#toRemove').val("true");
	    $('#editQuestionTopicId').val(questionTopicId);
	    submitMessageForm();  
    } */
    

    function selectexplanation()
    { 
    	
    	var hval=$('#explanation_type').val();
    		
    	if(hval==1){
    		$('#explanation_type_img').hide();
    	$('#explanation_type_text').hide();
    	
    		
    	}
    	else if(hval==21){$('#explanation_type_img').hide();$('#explanation_type_text').show();}
    	else if(hval==22 || hval==23){$('#explanation_type_img').show();$('#explanation_type_text').hide();}
    	
    	else{
    		$('#explanation_type_img').hide();
    		$('#explanation_type_text').hide();
    		
    		} 
    }


    function selecthint()
    { 
    	
    var hval=$('#hint_type').val();
    	
    if(hval==1){
    	$('#hint_type_img').hide();
        $('#hint_type_text').hide(); 
    }
    else if(hval==2){$('#hint_type_img').hide();$('#hint_type_text').show();}
    else if(hval==3 || hval==4){$('#hint_type_img').show();$('#hint_type_text').hide();}

    else{
    	$('#hint_type_img').hide();
    	$('#hint_type_text').hide();
    	
    	} 
    }

    
    function removeQuestion(questionId,questopicId)
	{
		if(confirm("Are you sure to delete this Question  ?") == false)
			return false;
		
		$.ajax
		({
			url:"${pageContext.request.contextPath}/admin/removequestion.htm",
			type:"POST",
			data: {"questionId": questionId.toString(),"questopicId": questopicId.toString()},
			success:function(data)
			{
				if(data == 'noquestionTopicId')
					alert("Please select Question");
				else if(data.indexOf('<html') == -1)			// Since error page will have <html> tag
				{
					//alert("Not deleted");
					alert("Question removed successfully");
					getQuestionData();
				}
			},
			error:function()
			{
				alert("Something went wrong");
			}
		});
	}
    
    

   $("#updateQuestionId").on("click", function()
    {
	     if(validateForm() != false)
		    submitMessageForm(); 
    });
     
    function validateForm()
    {
	    var status = false;
	    var question = $("#quesDescription").val();
	    
	    $(".errorCls2").empty();
	    
	    if($('#questionTypeId').val() == "")
	    {
		    $(".errorCls2").html("Please select question type");
		    return false;
	    }
	    
	    if($('#questionDifficultyLevelId').val() == "")
	    {
		    $(".errorCls2").html("Please select question difficulty level");
		    return false;
	    }
	    
	    if($('#questionCognitiveLevelId').val() == "")
	    {function getSelectedBoardID(){
	    	var cbseId=parseInt($("#boardId").val());
	    	var val='<option value="">Select Board To Map From...</option>';
	    	<c:forEach var='board' items="${boardList}">
	    	var id='${board.id}';
	    	if(parseInt(id) !=cbseId){
	    		val=val+'<option value="${board.id}">${board.name}</option>';
	    	}
	    </c:forEach>
	    $("#boardIdMap").empty().append(val);
	    } 
		    $(".errorCls2").html("Please select cognitive level");
		    return false;
	    }
	    
	    if(question == null || question == "")
	    {
		    var questionFile = $("#questionImgId").val();
		    if(questionFile == null || questionFile == "")
		    {
			    $(".errorCls2").html("Enter Question or upload question!");
			    $(".questionId").focus();
			    return false;
		    }
		    else
		    {
			    var val = questionFile.split(".");
			    if(val[val.length - 1] != "jpg" && val[val.length - 1] != "jpeg" && val[val.length - 1] != "png")
			    {
				    $(".errorCls2").html("Please browse png or jpg file!");
				    return false;
			    }
		    }
	    }
	    
	    var option1 = $("#optionDescription1").val();
	    if(option1 == null || option1 == "")
	    {
		    var option1File = $("#optionImgId1").val();
		    if(option1File == null || option1File == "")
		    {
			    $(".errorCls2").html("Enter option1 or upload option1!");
			    return false;
		    }
		    else
		    {
			    var val = option1File.split(".");
			    if(val[val.length - 1] != "jpg" && val[val.length - 1] != "jpeg" && val[val.length - 1] != "png")
			    {
				    $(".errorCls2").html("Please browse png or jpg file!");
				    return false;
			    }
		    }
	    }
	    
	    var option2 = $("#optionDescription2").val();
	    if(option2 == null || option2 == "")
	    {
		    var option2File = $("#optionImgId2").val();
		    if(option2File == null || option2File == "")
		    {
			    $(".errorCls2").html("Enter option2 or upload option2!");
			    return false;
		    }
		    else
		    {
			    var val = option1File.split(".");
			    if(val[val.length - 1] != "jpg" && val[val.length - 1] != "jpeg" && val[val.length - 1] != "png")
			    {
				    $(".errorCls2").html("Please browse png or jpg file!");
				    return false;
			    }
		    }
	    }
	    
	    var counter=0;
	    $(".correctAnsCls").each(function(e)
	    {
		    if($(this).prop('checked') == true)
		    {
		    	counter++;
			    status = true;
		    }
	    });
	    if(status == false)
	    {
		    $(".errorCls2").html("select atleast one correct answer!");
		    $(".errorCls2").focus();
		    return false;
	    }
	    
	    status = false;
	    $(".inCorrect").each(function(e)
	    {
		    if($(this).prop('checked') == true)
		    {
			    status = true;
		    }
	    });
	    
	    if(status == false)
	    {
		    $(".errorCls2").html("Atleast one answer must be in-correct!");
		    $(".errorCls2").focus();
		    return false;
	    }
	    
	    if ($("#questionTypeId").val()=='10' && counter>1){
    		$(".errorCls2").html("Only one answer must be correct!");
	        $(".errorCls2").focus();
	        return false;
    }else if($("#questionTypeId").val()=='16' && counter==1)
    	 {
    		$(".errorCls2").html("More than one answer should be correct!");
	        $(".errorCls2").focus();
	        return false;
    }
	    
	    
    }
    
   
    function submitMessageForm()
    {
	    $("#fullPageloader").show();
	    
	  /*   $("#questionFormId").submit(function(e)
	    {
		    var formData = new FormData(this);
		    var formAction = $(this).attr("action");
		    
		    $.ajax(
		    {
		        url : formAction,
		        type : "POST",
		        data : formData,
		        mimeType : "multipart/form-data",
		        contentType : false,
		        cache : false,
		        processData : false,
		        success : function(data)
		        {
			        $("#fullPageloader").hide();
			        if(data == "success")
			        {
				        $(".close").trigger('click'); // To hide the pop-up background
				        
				        if(addEditRemove == 'add')
					        alert("Question uploaded successfully");
				        if(addEditRemove == 'edit')
					        alert("Question edited successfully");
				        if(addEditRemove == 'remove')
					        alert("Question removed successfully");
				        
				        getQuestionData(); // Reload question data table
			        }
		        },
		        error : function()
		        {
			        
		        }
		    });
		    e.preventDefault(); //Stop default action
		    $(this).unbind("submit"); // Unbind to stop multiple form submit
	    }); */
	    $("#questionFormId").submit(); // Submit the form.				
    }

    function uploadQuestionExcelForm()
    {
	    var selectedFile = $('#excelFileId').val();
	    
	    if(selectedFile == '')
	    {
		    alert("Please select file");
		    return false;
	    }
	    
	    var extension = selectedFile.substring(selectedFile.lastIndexOf(".") + 1, selectedFile.length);
	    if(extension != 'xlsx' && extension != 'xls')
	    {
		    alert("Please select excel format file");
		    return false;
	    }
	    
	    $("#questionUploadForm").submit(function(e)
	    {
		    var formData = new FormData(this);
		    var formAction = $(this).attr("action");
		    
		    $.ajax(
		    {
		        url : formAction + "?topicId=${globalVO.topicId}&loId=${globalVO.loId}",
		        type : "POST",
		        data : formData,
		        mimeType : "multipart/form-data",
		        contentType : false,
		        cache : false,
		        processData : false,
		        success : function(data)
		        {
			        if(data == "success")
			        {
				        alert("Question uploaded successfully");
				        $('.close').trigger('click'); // To hide the pop-up background
			        }
			        else
				        alert(data);
			        
		        },
		        error : function()
		        {
			        
		        }
		    });
		    e.preventDefault(); //Stop default action
		    $(this).unbind("submit"); // Unbind to stop multiple form submit
	    });
	    $("#questionUploadForm").submit(); // Submit the form.	
    }
    
    
     function enableDisableQuest(questStatus, questId, statusId){
    	questStatus=parseInt(questStatus); 
    	if(questStatus==1){
    		if(confirm("Are you sure to Inactive this question?") == false)
    		    return false;
    		
    	 	
    		$.post("${pageContext.request.contextPath}/ajax/rest/enableDisableQuestion/"+questId+"/"+2, function(data){
    			if(data==true || data=='true'){
    				alert("Question Disable successfully !!!");
    			$("#enDisQuest"+statusId).html("Inactive");
        		$("#enDisQuest"+statusId).attr("onclick","enableDisableQuest("+2+","+questId+","+statusId+")");
    			}
    		}); 
    		
    	}else{
    		if(confirm("Are you sure to Active this question?") == false)
    		    return false;
    	
 $.post("${pageContext.request.contextPath}/ajax/rest/enableDisableQuestion/"+questId+"/"+1, function(data){
	 alert("Question Enable successfully !!!");
		$("#enDisQuest"+statusId).html("Active");
		$("#enDisQuest"+statusId).attr("onclick","enableDisableQuest("+1+","+questId+","+statusId+")");
    		}); 
    	}
    }
     
     
     
     
     
     function saveLo()
 	{
 		var boardId = $('#boardId').val();
 		var gradeId = $('#sectionId option:selected').attr("gradeid");
 		var subjectId = $('#subjectId').val();
 		var chapterId = $('#chapterId').val();		
 		
 		var topicId=$('#topicId').val();
 		var loName= $('#LoName').val();
 		if(loName == '')
 		{
 			alert("Please type loName");
 		}
 		else
 		{
 			$.ajax
 			({
 				url:"${pageContext.request.contextPath}/admin/addTopicLoPage.htm",
 				type:"POST",
 				data: {"boardId": boardId, "gradeId": gradeId, "subjectId":subjectId, "chapterId": chapterId, "topicId": topicId,"loName": loName},
 				success:function(data)
 				{
 					if(data == 'noBoard')
 						alert("Please select board");
 					else if(data == 'noGrade')
 						console.log("No grade id");
 					else if(data == 'noSubject')
 						alert("Please select subject");
 					else if(data == 'noChapter')
 						alert("Please select chapter");
 					else if(data == 'noTopic')
 						alert("Please type topic");
 					else if(data == 'topicNotAvailable')
 						alert("Topic already exists");
 					else if(data == 'More Than 3 Lo not Inserted')
 						alert("More Than 3 Lo not Inserted");
 					else if(data.indexOf('<html') == -1)			// Since error page will have <html> tag
 					{
 						$(".close").trigger('click');				// To hide the pop-up background
 						alert("successfully created");
 						loadDataPage();
 					}
 				},
 				error:function()
 				{
 					alert("Something went wrong");
 				}
 			});
 		}
 	}
    
</script>

<div style="width: 90%; margin: 0px auto;">
	<div>
	    <button class="btn btn-success " data-toggle="modal" data-target="#addlo" onclick="addQuestion()">Add LO</button>
	<!-- 	<button class="btn btn-success " data-toggle="modal" data-target="#myModal4" onclick="addQuestion()">Add New</button>
	 -->	<!-- <button class="btn btn-primary" data-toggle="modal" data-target="#myModal2">Edit</button> -->
		<!-- <button class="btn btn-danger" data-toggle="modal" data-target="#myModal3" onclick="removeTopic()">Remove</button> -->
		<!-- <button class="btn btn-success" data-toggle="modal" data-target="#myModal5"  style="float: right" onclick = "getSelectedBoardID1()">Mapping</button> 
		 -->
		<button type="submit" class="btn btn-warning" data-toggle="modal" data-target="#uploadExcel" id="questionUpload">Import From Excel</button>
		<a class="btn btn-warning" href="${pageContext.request.contextPath}/admin/downloadQuestion.htm?topicId=${globalVO.topicId}"> Download Questions</a>
		<button style="float: right" type="button" class="btn btn-warning" data-toggle="modal" data-target="#excelFormat" id="exportExcel">Download Excel Format</button>
		<br>
		<br>

		<table style="border: 3px; background: rgb(243, 244, 248); width: 100%">
			<!-- Use it because it's helpful -->
			<tr style="width: 100%; height: 100%;">
				<td>
					<table id="dataTableId" class="display" style="width: 100%">
						<thead>
							<tr class="dataTab" style="font-size:15px;">
								<th>Sr. No.</th>
								<th>QuestionId</th>
								<th>Question</th>
								<th>Level</th>
								<th class="text-center">Question Image</th>
								<th> Author Name</th>
							<!-- 	<th> Status</th> -->
							     <th>Hint/Resource</th>
							      <th>View Explanation</th>
								<th>Action</th>
								
							</tr>
						</thead>
						<tbody>
							<c:forEach var="quesTopic" items="${questionTopic}" varStatus="status">
								<tr style=" font-size:15px;">
									<td>${status.count}</td>
										<td><p style="font-weight:bold;">${quesTopic.question.id }</p></td>
									<td><p style="font-weight:bold;">${quesTopic.question.description} </p>
									   <%--  console.log("question id"+${quesTopic.question.id}); --%>
									
									 <c:forEach var="ans"
											items="${quesTopic.question.answers}" varStatus="stats">
												<p style="float:left">${stats.count}. &nbsp;
											${ans.description} &nbsp; 
											 <c:if test="${not empty ans.imagePath}">
											<img src="${commonPath}/questionImages/${ans.imagePath}" />
										     </c:if>
										     </p>

										</c:forEach> <!-- Answers are under working -->
										<div class="col-md-12" style="border-top: 1px solid #dddddd; margin-top: 10px; padding: 10px;">
<%-- <div class="col-md-5" style="color: #ffffff; "><img	src="${pageContext.request.contextPath}/resources/images/like-icon.png" style="max-width:100%"> Like</div> --%>
<%-- <div class="col-md-5" style=" padding: 0px; font-size:13px; "><a href="${pageContext.request.contextPath}/admin/getQuestionDetailsById.htm" class="message"><i class="fa fa-comments" aria-hidden="true"></i> Comment</a></div> --%>
<c:if test="${not empty quesTopic.question.questionComment && quesTopic.question.questionComment.size()>0 }"><div class="col-md-5" style=" padding: 0px; font-size:13px; "><a href="${pageContext.request.contextPath}/admin/getQuestionDetailsById/${quesTopic.question.id }.htm" class="message"><i class="fa fa-comments" aria-hidden="true"></i> Feedback (${quesTopic.question.questionComment.size() })</a></div></c:if>
</div>
		                            </td>
									<td>${quesTopic.difficultyLevel.name }</td>
									<td>
									<c:if test="${not empty quesTopic.question.imagePath}">
									<img src="${commonPath}/questionImages/${quesTopic.question.imagePath}" />
									</c:if>
									<c:if test="${empty quesTopic.question.imagePath}">N/A</c:if>
									</td>
									<td>${quesTopic.question.user.firstName}</td> 
									<td>
									<c:if test="${not empty quesTopic.question.hinttypeid}"> 
									<c:if test="${quesTopic.question.hinttypeid=='4'}">
			                        <video controls class="img-responsive">
				                    <source src="${commonPath}/questionImages/${quesTopic.question.hintContent}" type="video/mp4"> </video>
		                            </c:if> 
									<c:if test="${not empty quesTopic.question.hinttypeid && quesTopic.question.hinttypeid==3}"> 
									<img src="${commonPath}/questionImages/${quesTopic.question.hintContent}" />
									</c:if> 
									<c:if test="${not empty quesTopic.question.hinttypeid && quesTopic.question.hinttypeid==2}">
			                        <p>${quesTopic.question.hintContent}</p>
		                             </c:if> 
									</c:if> 
									<c:if test="${empty quesTopic.question.hinttypeid}">
									None
									</c:if> 
									</td>
									<td>
									<c:if test="${quesTopic.question.explanationId=='21'}">
						             <p>${quesTopic.question.explanationContent}</p> 
					       </c:if>
					       
					       <c:if test="${quesTopic.question.explanationId=='22'}">
					<a class="example-image-link" href="${quesTopic.question.explanationContent}" data-lightbox="example-1">
						<img src="${commonPath}/questionImages/${quesTopic.question.explanationContent}" class="img-responsive" />
							 </a>
							 </c:if>
					       
					       <c:if test="${quesTopic.question.explanationId=='23'}">
						<video controls class="img-responsive">
							<source src="${commonPath}/questionImages/${quesTopic.question.explanationContent}"
								type="video/mp4">
						</video>
					</c:if>
									
									
									
									</td>
									<%-- <td><a href="javascript:void(0)" onclick="enableDisableQuest(${quesTopic.status},${quesTopic.question.id},${status.count })" id="enDisQuest${status.count}"><c:choose><c:when test="${quesTopic.status eq 1 }">Active</c:when><c:otherwise>Inactive</c:otherwise></c:choose></a></td> --%>
									<td>
										<a href="javascript:void(0)" data-toggle="modal" data-target="#myModal4" onclick="editQuestion('${quesTopic.question.id}')">Edit</a>
										 /
									<%-- 	<a href="javascript:void(0)" onclick="removeQuestion('${quesTopic.id}','${quesTopic.question.id}')">Remove</a>
									 --%>
									 
									 	<a href="javascript:void(0)" onclick="removeQuestion('${quesTopic.question.id}','${quesTopic.id}')">Remove</a>
								
									 
									 </td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</td>
			</tr>
		</table>
	</div>

	<!-- excelModal -->
	<div class="modal fade" id="uploadExcel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Upload from Excel</h4>
				</div>

				<div class="clearfix"></div>
				<div class="modal-body" style="border-bottom: none !important">
					<span id="studentExcelId"></span>
					<form id="questionUploadForm" method="post" action="${pageContext.request.contextPath}/admin/uploadLoQuestionExcelSheet" enctype="multipart/form-data">
						<!-- File input -->
						<input name="file2" id="excelFileId" type="file" /><br />

						<a class="btn btn-warning" href="javascript:void(0)" onclick="uploadQuestionExcelForm()">Upload</a>
					</form>
					<div id="result"></div>
				</div>
			</div>
		</div>
	</div>
	<!-- excelModal -->
	
	
	<!-- addlo -->
	<div class="modal fade" id="addlo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Add Lo</h4>
				</div>

				<div class="clearfix"></div>
				<div class="modal-body" style="border-bottom: none !important">
					<input class="" type="text"  id="LoName" placeholder="Lo Name" aria-controls="dataTableId">
					
					
					<div style="text-align: center">
					 
					<button class="btn btn-primary" onclick = "saveLo()">AddLO</button>
					
					</div>
				</div>
			</div>
		</div>
	</div>
	
	

	<!-- excelFormaModal -->
	<div class="modal fade" id="excelFormat" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">Download Excel Format</h4>
				</div>

				<div class="clearfix"></div>
				<div class="modal-body" style="border-bottom: none !important">
					<ul>
						<li>Please do not delete header row from sheet.</li>
					</ul>
					<div style="text-align: center">
						<a class="btn btn-warning" href="${pageContext.request.contextPath}/admin/downloadQuestionExcelFormat">Download</a>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- excelFormatModal -->

	<!-- Modal4 -->
	<div id="myModal4" class="modal fade" role="dialog">
		<div class="eng-img img-responsive" id="engImg"></div>
		<div class="modal-dialog assign-name-pop" style="width: 780px !important">

			<!-- Modal content-->
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<div class="modal-title">
						<span id="headerId"></span> Questions
					</div>
				</div>

				<div class="modal-body assign-name-pop-mid panel-body2">

					<div class="col-md-12">
						<div class="row">

							<div class="edit-quest">

								<form:form action="${pageContext.request.contextPath}/admin/uploadQuestionUpdate.htm"  method="POST" modelAttribute="globalVO" enctype="multipart/form-data" id="questionFormId" >
									<div class="new-question">

										<!--  <div class="col-md-3 col-sm-3">
							            <select name="">
							            <option>Question Category</option>
							            <option>Text Based</option>
							            <option>Image Based</option>
							            <option>Video Based</option>
							            </select>
							            </div> -->

										<input type="hidden" name="questionId" id="editQuestionId" value="" /> <input type="hidden" name="optionId1" id="editOptionId1" value="" /> <input type="hidden" name="optionId2" id="editOptionId2" value="" /> <input type="hidden" name="optionId3" id="editOptionId3" value="" /> <input type="hidden" name="optionId4" id="editOptionId4" value="" /> <input type="hidden" name="questionTopicId" id="editQuestionTopicId" value="" /> <input type="hidden" name="topicId" value="${globalVO.topicId}" />
										<form:hidden path="loginUserId" value="${user.id}" />
										<form:hidden path="questionCategoryId" value="2" />
										<!-- Right now we are using only question with image type questions. so providing static value -->

										<div class="col-md-4 col-sm-3">
											<select id="questionTypeId" name="questionTypeId" required="required">
												<option value="">Question Type</option>
												<c:forEach var="questionType" items="${globalVO.questionTypeList}">
													<option value="${questionType.id}">${questionType.name}</option>
												</c:forEach>
											</select>
										</div>

										<div class="col-md-4 col-sm-3">
											<select id="questionDifficultyLevelId" name="questionDifficultyLevelId" required="required">
												<option value="">Difficulty Level</option>
												<c:forEach var="questionDifficulty" items="${globalVO.questionDifficultyLevelList}">
													<option value="${questionDifficulty.id}">${questionDifficulty.name}</option>
												</c:forEach>
											</select>
										</div>

										<div class="col-md-4 col-sm-3">
											<select id="questionCognitiveLevelId" name="questionCognitiveLevelId" required="required">
												<option value="">Cognitive Level</option>
												<c:forEach var="questionCognitive" items="${globalVO.questionCognitiveLevelList}">
													<option value="${questionCognitive.id}">${questionCognitive.name}</option>
												</c:forEach>
											</select>
										</div>
										
										
										<div class="col-md-4 col-sm-3">
											<select id="zoneId" name="zoneId" required="required">
												<option value="">Zone List</option>
												<c:forEach var="zoneList" items="${globalVO.zoneList}">
													<option value="${zoneList.id}">${zoneList.name}</option>
												</c:forEach>
											</select>
										</div>

									</div>
									<div class="clearfix"></div>
<div class="col-md-12 col-sm-12 mrt20" style="border-bottom: solid 1px #ccc;margin-top:15px; margin-bottom: 15px;">
									<div class="col-md-6 col-sm-6 mrt20">
										<label>Question</label>
										<input type="checkbox" name="questionImgCheck" ><!--  -->Remove Image</input>
										<textarea class=" mrt10" name="question" cols="" rows="" id="quesDescription"></textarea>
									</div>
									<div class="col-md-4 col-sm-4 ques-file-fld">
										<input name="questionImage" type="file" id="questionImgId">

									</div>

									<div class="col-md-2 col-sm-2 col-xs-12 ques-file-fld">
										<div id="quesImageDivId" class="enlarge"></div>

									</div>
									
									</div>
									<div class="clearfix"></div>
<div class="col-md-12 col-sm-12 mrt20" style="border-bottom: solid 1px #ccc;margin-top:15px; margin-bottom: 15px;">
									<div class="col-md-6 col-xs-12 col-sm-6 mrt20">
										<label>Option 1</label>
										<input type="checkbox" name="option1ImgCheck" ><!--  -->Remove Image</input>
										<textarea name="option1" class="mrt10" cols="" rows="" id="optionDescription1"></textarea>
									</div>
									<div class="col-md-4 col-sm-4 col-xs-12 ques-file-fld">
										<input name="option1Image" type="file" id="optionImgId1"> <input name="isCorrectOption1" type="radio" value="1" class="correctAnsCls mr-rd"><label class="mr-rd">Correct</label> <input name="isCorrectOption1" type="radio" value="0" class="inCorrect mr-rd"><label class="mr-rd">Incorrect</label>
									</div>


									<div class="col-md-2 col-sm-2 col-xs-12 ques-file-fld">
										<div id="ansImageDivId1" class="enlarge"></div>

									</div>
</div>

									<!-- 
								<div class="col-md-2 col-sm-2 col-xs-5 ques-file-fld">
									
								</div> -->

									<div class="clearfix"></div>

<div class="col-md-12 col-sm-12 mrt20" style="border-bottom: solid 1px #ccc;margin-top:15px; margin-bottom: 15px;">
									<div class="col-md-6 col-sm-6 col-xs-12 mrt20">
										<label>Option 2</label>
										<input type="checkbox" name="option2ImgCheck" ><!--  -->Remove Image</input>
										<textarea name="option2" class="mrt10" cols="" rows="" id="optionDescription2"></textarea>
									</div>
									<div class="col-md-4 col-sm-4 col-xs-12 ques-file-fld">
										<input name="option2Image" type="file" id="optionImgId2"> <input name="isCorrectOption2" type="radio" value="1" class="correctAnsCls mr-rd"><label class="mr-rd">Correct</label> <input name="isCorrectOption2" type="radio" value="0" class="inCorrect mr-rd"><label class="mr-rd">Incorrect</label>
									</div>

									<div class="col-md-2 col-sm-2 col-xs-12 ques-file-fld">
										<div id="ansImageDivId2" class="enlarge"></div>
									</div>
									<!-- <div class="col-md-2 col-sm-2 col-xs-5 ques-file-fld">
									
								</div> -->
</div>
									<div class="clearfix"></div>
<div class="col-md-12 col-sm-12 mrt20" style="border-bottom: solid 1px #ccc;margin-top:15px; margin-bottom: 15px;">
									<div class="col-md-6 col-xs-12 col-sm-6 mrt20">
										<label>Option 3</label>
										<input type="checkbox" name="option3ImgCheck" ><!--  -->Remove Image</input>
										<textarea name="option3" class="mrt10" cols="" rows="" id="optionDescription3"></textarea>
									</div>
									<div class="col-md-4 col-sm-4 col-xs-12 ques-file-fld">
										<input name="option3Image" type="file" id="optionImgId3"> <input name="isCorrectOption3" type="radio" value="1" class="correctAnsCls mr-rd"><label class="mr-rd">Correct</label> <input name="isCorrectOption3" type="radio" value="0" class="inCorrect mr-rd"><label class="mr-rd">Incorrect</label>
									</div>

									<div class="col-md-2 col-sm-2 col-xs-12 ques-file-fld">
										<div id="ansImageDivId3" class="enlarge"></div>
									</div>
									<!-- <div class="col-md-2 col-sm-2 col-xs-5 ques-file-fld">
									
								</div> -->
</div>
									<div class="clearfix"></div>
									<div class="col-md-12 col-sm-12 mrt20" style="border-bottom: solid 1px #ccc;margin-top:15px; margin-bottom: 15px;">

									<div class="col-md-6 col-sm-6 col-xs-12 mrt20">
										<label>Option 4</label>
										<input type="checkbox" name="option4ImgCheck" ><!--  -->Remove Image</input>
										<textarea name="option4" class="mrt10" cols="" rows="" id="optionDescription4"></textarea>
									</div>

									<div class="col-md-4 col-sm-4 col-xs-12 ques-file-fld">
										<input name="option4Image" type="file" id="optionImgId4"> <input name="isCorrectOption4" type="radio" value="1" class="correctAnsCls mr-rd"><label class="mr-rd">Correct</label> <input name="isCorrectOption4" type="radio" value="0" class="inCorrect mr-rd"><label class="mr-rd">Incorrect</label>

									</div>

									<div class="col-md-2 col-sm-2 col-xs-12 ques-file-fld">
										<div id="ansImageDivId4" class="enlarge"></div>
									</div>
									<!-- div class="col-md-2 col-sm-2 col-xs-5 ques-file-fld">
									
								</div> -->
                                       </div>
									<div class="clearfix"></div>
									
									 <div class="clearfix"></div>
									 
									<div class="col-md-12 col-sm-12 mrt20" style="border-bottom: solid 1px #ccc;margin-top:15px; margin-bottom: 15px;">
										<div class="col-md-6 col-sm-6 col-xs-12 mrt20">

										 
											<select id="hint_type" name="hintTypeTextSelector" required="required"
												class="col-md-6"  onchange="selecthint()">
												<option value="1">Hint type</option>
												<option value="2">Text</option>
												<option value="3">Image</option>
												<option value="4">Video</option>
											</select>
										</div>
										

										<div class="col-md-4 col-sm-4 col-xs-12 ques-file-fld">
											<input name="hintTypeImg" type="file" id="hint_type_img">
											<textarea name="hintTypeText" class="mrt10" cols="" rows=""
												id="hint_type_text"></textarea>

										</div>



										<div class="col-md-2 col-sm-2 col-xs-12 ques-file-fld">
											<div id="hintImageDivId4" class="enlarge"></div>
										</div>

									</div>
									 
									<div class="clearfix"></div>
									<div class="col-md-12 col-sm-12 mrt20"
										style="border-bottom: solid 1px #ccc; margin-bottom: 15px;">
										<div class="col-md-6 col-sm-6 col-xs-12 mrt20">


											<select id="explanation_type" name="explanationTypeSelector" required="required"
												class="col-md-6" onchange="selectexplanation()">
												<option value="1">Explanation type</option>
												<option value="21">Text</option>
												<option value="22">Image</option>
												<option value="23">Video</option>
											</select>
										</div>

										<div class="col-md-4 col-sm-4 col-xs-12 ques-file-fld">
											<input name="explanationTypeImg" type="file" id="explanation_type_img">
											<textarea name="explanationTypeText" class="mrt10" cols="" rows=""
												id="explanation_type_text"></textarea>

										</div>



										<div class="col-md-2 col-sm-2 col-xs-12 ques-file-fld">
											<div id="explanationImageDivId4" class="enlarge"></div>
										</div>

									</div>
									

									<span class="errorCls2" style="color: red;"></span><br>
									<div class="col-md-2 mrt20">
										<div class="col-md-2 mrt20">
											<a href="javascript:void(0)" class="btn btn-danger" id="updateQuestionId">Update Question </a>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>

				</div>

				<div class="modal-footer"></div>
			</div>

		</div>
	</div>
	<!-- Modal4 end -->
</div>