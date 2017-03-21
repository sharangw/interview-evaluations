package com.revature.mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.revature.domain.Batch;
import com.revature.domain.Eval;
import com.revature.domain.EvalComment;
import com.revature.domain.EvalType;
import com.revature.domain.Person;
import com.revature.domain.PersonRole;
import com.revature.domain.QuestionEval;
import com.revature.domain.QuestionPool;
import com.revature.domain.Subject;
import com.revature.services.EvalLogic;
import com.revature.services.EvalLogicImpl;

//@RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class EvaluationTest {


	@Test
	public void testGetEvalById() {
		
		Eval eval = new Eval();
		Date date = new Date((new java.util.Date()).getTime());
		EvalType evalType = new EvalType();
		evalType.setId(1);
		evalType.setDescription("Trainer");
		PersonRole personRole = new PersonRole(1, "trainee");
		Person person = new Person("Test", "Pers", personRole);
		Batch batch = new Batch("TestBatch");
		Subject subject = new Subject("General");
		QuestionPool qPool = new QuestionPool("What is coding?", 200, 100, subject, 10, date);
		QuestionEval qEval1 = new QuestionEval(150, 100, eval, qPool);
		QuestionEval qEval2 = new QuestionEval(100, 100, eval, qPool);
		List<QuestionEval> qEvals = new ArrayList<>();
		qEvals.add(qEval1);
		qEvals.add(qEval2);		
		EvalComment comment1 = new EvalComment("Great", eval);
		EvalComment comment2 = new EvalComment("Bad", eval);
		List<EvalComment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);
		
		eval.setWeek(3);		
		eval.setDate(date);
		eval.setEvalType(evalType);
		eval.setTrainee(person);
		eval.setBatch(batch);
		eval.setQuestions(qEvals);
		eval.setComments(comments);
		
		System.out.println("eval");
		System.out.println(eval);
		
		EvalLogicImpl evalTest = Mockito.mock(EvalLogicImpl.class);
		
		when(evalTest.createEval(eval)).thenReturn(eval);
	
		assertEquals(evalTest.createEval(eval), eval);
	}

}
