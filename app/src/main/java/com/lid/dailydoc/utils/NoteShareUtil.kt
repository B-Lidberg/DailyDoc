package com.lid.dailydoc.utils

import com.lid.dailydoc.data.extras.surveyQuestions
import com.lid.dailydoc.data.model.Note
import com.lid.dailydoc.other.Constants.BULLET

fun getNoteInfo(note: Note) =

"""
DailyDoc Note Details:
       
Date: ${note.date}
       
Summary: 
$BULLET ${note.summary}
  
Body: 
$BULLET ${note.body} 
      
Surveys:
 1. ${surveyQuestions[0]}: 
  $BULLET ${note.survey1}  
 2. ${surveyQuestions[1]}: 
  $BULLET ${note.survey2}   
 3. ${surveyQuestions[2]}: 
  $BULLET ${note.survey3}
""".trimIndent()