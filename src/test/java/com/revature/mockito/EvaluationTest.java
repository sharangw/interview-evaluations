package com.revature.mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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

/**
 * Create mock objects for Evaluation Logic at runtime and test them, simulating the database.
 * @author sharang
 *
 */
//@RunWith attaches a runner with the test class to initialize the test data
@RunWith(MockitoJUnitRunner.class)
public class EvaluationTest {

	@Mock
	private EvalLogicImpl evalTest = Mockito.mock(EvalLogicImpl.class);

	@Test
	public void testCreateEval() {
		
		Eval eval = creatingEval();
		
		System.out.println("Eval:");
		System.out.println(eval+"\n");
		
		when(evalTest.createEval(eval)).thenReturn(eval);
		Eval createdEval = evalTest.createEval(eval);
		System.out.println(createdEval+"\n");
		
		assertEquals(createdEval, eval);
	}
	
	@Test
	public void testCreateComment() {
		
		EvalComment comment = creatingComment();
		
		System.out.println("Comment:");
		System.out.println(comment+"\n");
		
		when(evalTest.createComment(comment, creatingEval().getId())).thenReturn(comment);
		EvalComment createdComment = evalTest.createComment(comment, creatingEval().getId());
		System.out.println(createdComment+"\n");
		
		assertEquals(createdComment, comment);		
	}

	@Test
	public void testUpdateEval() {
		
		Eval uEval = updatingEval();
		
		System.out.println("Updated Eval:");
		System.out.println(uEval+"\n");
	
		when(evalTest.updateEval(uEval, uEval.getId())).thenReturn(uEval);
		Eval updatedEval = evalTest.updateEval(uEval, uEval.getId());
		System.out.println(updatedEval+"\n");
		
		assertEquals(updatedEval, uEval);		
	}
	
	@Test
	public void testUpdateComment() {
		
		EvalComment uComment = updatingComment();
		
		System.out.println("Updated Comment:");
		System.out.println(uComment+"\n");
		
		when(evalTest.updateComment(uComment, uComment.getId())).thenReturn(uComment);
		EvalComment updatedComment = evalTest.updateComment(uComment, uComment.getId());
		System.out.println(updatedComment+"\n");
		
		assertEquals(updatedComment, uComment);
	}
	
	@Test
	public void testDeleteEval() {
		
		int evalId = creatingEval().getId();  
				
		System.out.println("Deleted Eval:");
		System.out.println(evalId+"\n");
		
		String deleted = "{\"text\":\"Evaluation: " + evalId + " - DELETED\"}";
		
		when(evalTest.deleteEval(evalId)).thenReturn(deleted);
		String deletedEval = evalTest.deleteEval(evalId);
		System.out.println(deletedEval+"\n");
		
		assertEquals(deletedEval, deleted);
	}

	@Test
	public void testDeleteComment() {
		
		int commentId = creatingComment().getId();
		
		System.out.println("Deleted Comment:");
		System.out.println(commentId+"\n");
		
		String deleted = "{\"text\":\"Evaluation Comment: " + commentId + " - DELETED\"}";
		
		when(evalTest.deleteComment(commentId)).thenReturn(deleted);
		String deletedComment = evalTest.deleteComment(commentId);
		System.out.println(deletedComment+"\n");
		
		assertEquals(deletedComment, deleted);
		
	}
	
	// Creating:

	private Eval creatingEval() {
		
		// Creating an evaluation - requires other dependent objects to be created first:
		Eval eval = new Eval();
		
		// Date
		Date date = new Date((new java.util.Date()).getTime());
		
		// EvalType
		EvalType evalType = new EvalType();
		evalType.setId(1);
		evalType.setDescription("Trainer");
		
		// Person
		PersonRole personRole = new PersonRole(1, "trainee");
		Person person = new Person("Test", "Pers", personRole);
		
		// Batch
		Batch batch = new Batch("TestBatch");
		
		// Subject
		Subject subject = new Subject("General");
		
		// QuestionPool
		QuestionPool qPool = new QuestionPool("What is coding?", 200, 100, subject, 10, date);
		
		// QuestionEvals
		QuestionEval qEval1 = new QuestionEval(150, 100, eval, qPool);
		QuestionEval qEval2 = new QuestionEval(100, 100, eval, qPool);
		List<QuestionEval> qEvals = new ArrayList<>();
		qEvals.add(qEval1);
		qEvals.add(qEval2);		
		
		// EvalComments
		EvalComment comment1 = new EvalComment("Great", eval);
		EvalComment comment2 = new EvalComment("Bad", eval);
		List<EvalComment> comments = new ArrayList<>();
		comments.add(comment1);
		comments.add(comment2);
		
		// set eval object
		eval.setId(1);
		eval.setWeek(3);		
		eval.setDate(date);
		eval.setEvalType(evalType);
		eval.setTrainee(person);
		eval.setBatch(batch);
		eval.setQuestions(qEvals);
		eval.setComments(comments);
		
		return eval;
	}
	
	private EvalComment creatingComment() {
		
		EvalComment comment = new EvalComment("No Comment", creatingEval());
		comment.setId(100);
		
		return comment;	 
	}
	
	// Updating:
	
	private Eval updatingEval() {
		
		Eval eval = creatingEval();
		
		eval.setWeek(4);
		
		return eval;
	}
	
	private EvalComment updatingComment() {
		
		EvalComment comment = creatingComment();
		
		comment.setCommentText("Updated");
		
		return comment;
	}
	
}
