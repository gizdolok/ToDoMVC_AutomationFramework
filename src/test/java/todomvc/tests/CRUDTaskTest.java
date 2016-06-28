package todomvc.tests;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import todomvc.pageobjects.ToDosList;
import todomvc.webdriver.DriverSingleton;

import static org.junit.Assert.*;
import static todomvc.webdriver.DriverSingleton.WaitInSeconds;

/**
 * Created by Daniel Kolodzig on 25.09.2015.
 */

public class CRUDTaskTest {

    //This test suite ensures that a new task can be SUCCESSFULLY CREATED, RED, UPDATED and DELETED

    private String taskExample = "something that needs to be done";

    @Before
    public void SetUp(){
        DriverSingleton.getDriver();
        ToDosList.GoTo();

        assertTrue("ERROR OCCURRED! The Todos List Cannot Be Display", ToDosList.IsVisible());
    }

    @Test
    public void CreateNewTaskSucceeds() throws Exception{

        ToDosList.TypeNewTaskWithText(taskExample)
                 .CreateTask();

        assertEquals("TEST FAILED! The Task Was Not Successfully Created", 1, ToDosList.NumberOfTasksDisplayed());
    }

    @Test
    public void UserCanReadTask() throws Exception{
        String text_of_the_task;

        ToDosList.TypeNewTaskWithText(taskExample)
                .CreateTask();

        text_of_the_task = ToDosList.ReadTaskNumber(1);

        assertEquals("TEST FAILED! User Cannot Successfully Read The Task", taskExample, text_of_the_task);
    }

    @Test
    public void UpdatedTask() throws Exception{
        String new_text_of_the_task;

        ToDosList.TypeNewTaskWithText(taskExample)
                .CreateTask();

        WaitInSeconds(2);

        new_text_of_the_task = ToDosList.UpdateTaskNumberWithText(1 , "New thing to do");

        assertEquals("TEST FAILED! The Task Was Not Successfully Updated", new_text_of_the_task, ToDosList.ReadTaskNumber(1));
    }

    @Test
    public void DeleteTask() throws Exception{

        ToDosList.TypeNewTaskWithText(taskExample)
                .CreateTask();

        WaitInSeconds(2);

        ToDosList.DeleteTaskNumber(1);

        assertEquals("TEST FAILED! The Task Was Not Successfully Deleted", 0, ToDosList.NumberOfTasksDisplayed());
    }

    @After
    public void tearDown(){
        //Wait 3 seconds just to have better visibility of what the test is doing
        WaitInSeconds(3);
        //Delete all tasks to clean the environment for next test
        ToDosList.DeleteAllTasks();
        //See the browser closing
        WaitInSeconds(1);
        DriverSingleton.closeAndResetDriver();
    }
}

