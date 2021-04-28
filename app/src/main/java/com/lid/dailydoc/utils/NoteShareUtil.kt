package com.lid.dailydoc.utils

import com.lid.dailydoc.data.extras.bullet
import com.lid.dailydoc.data.extras.surveyQuestions
import com.lid.dailydoc.data.model.Note

fun getNoteInfo(note: Note) =

"""
DailyDoc Note Details:
       
Date: ${note.dateCreated}
       
Summary: 
$bullet ${note.summary}
  
Body: 
$bullet ${note.body} 
      
Surveys:
 1. ${surveyQuestions[0]}: 
  $bullet ${note.survey1}  
 2. ${surveyQuestions[1]}: 
  $bullet ${note.survey2}   
 3. ${surveyQuestions[2]}: 
  $bullet ${note.survey3}
""".trimIndent()