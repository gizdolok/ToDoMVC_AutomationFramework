package todomvc.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import todomvc.webdriver.DriverSingleton;

/**
 * Created by kolod on 26/06/2016.
 */
public class ToDosList {

    static Actions actions;

    static By todosListLocator      = By.cssSelector("section.todoapp");
    static By newTaskLocator        = By.cssSelector("input.new-todo");
    static By allTasksLocator       = By.xpath("//ul[@class='todo-list']/li");
    static By selectAllTasksLocator = By.cssSelector("input.toggle-all");
    static By clearCompletedLocator = By.cssSelector("footer.footer button.clear-completed");

    public static void GoTo() {
        DriverSingleton.visit(DriverSingleton.getBaseAddress());
    }
    
    public static boolean IsVisible() {
        DriverSingleton.finishLocatorPresent(todosListLocator);
        return DriverSingleton.isDisplayed(todosListLocator); }


    public static ToDosListCommand TypeNewTaskWithText(String task) {
        return new ToDosListCommand(task);
    }

    public static void SelectAllTasks() {
        DriverSingleton.click(selectAllTasksLocator);
    }

    private static void selectTaskNumber(int taskNumber) {
        (DriverSingleton.find(allTasksLocator).findElement(By.xpath("\u002E[" + taskNumber + "]/div[@class='view']/input[@class='toggle']"))).sendKeys(Keys.SPACE);
    }

    public static void ClickClearCompletedBtn() {
        if(DriverSingleton.isDisplayed(clearCompletedLocator))
            DriverSingleton.click(clearCompletedLocator);
    }

    public static Boolean DeleteAllTasks() {
        int number_of_tasks = getNumberOfTasks();
        if (number_of_tasks > 0){
            SelectAllTasks();
            ClickClearCompletedBtn();
            return true;
        } else {
           System.out.println("All Tasks Are Already Deleted");
           return false;
        }
    }

    public static String ReadTaskNumber(int taskNumber) {

        int number_of_tasks = getNumberOfTasks();

        if (number_of_tasks > 0){
            return getTextOfTaskNumber(taskNumber);
        } else {
            System.out.println("An ERROR OCCURRED! No Tasks found while trying to Read");
            return null;
        }
    }

    private static int getNumberOfTasks() {
        return DriverSingleton.findMany(allTasksLocator).size();
    }

    private static String getTextOfTaskNumber(int taskNumber) {
        return (DriverSingleton.find(allTasksLocator).findElement(By.xpath("\u002E[" + taskNumber + "]"))).getText();
    }

    public static int NumberOfTasksDisplayed() {
        return getNumberOfTasks();
    }

    public static String UpdateTaskNumberWithText(int taskNumber, String newText) {
        actions = new Actions(DriverSingleton.getDriver());
        WebElement taskElement = (DriverSingleton.find(allTasksLocator).findElement(By.xpath("\u002E[" + taskNumber + "]")));
        actions.doubleClick(taskElement).
                keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).sendKeys(Keys.BACK_SPACE).
                sendKeys(newText).
                build().perform();

        return getTextOfTaskNumber(taskNumber);
    }

    public static void DeleteTaskNumber(int i) {
        selectTaskNumber(i);
        ClickClearCompletedBtn();
    }

    public static class ToDosListCommand
    {
        private String task;

        ToDosListCommand(String task) {
            this.task = task;
        }

        public ToDosListCommand CreateTask() throws Exception {
            DriverSingleton.type(task, newTaskLocator);
            DriverSingleton.pressKey(Keys.ENTER, newTaskLocator);
            return this;
        }

        public ToDosListCommand MarkTask() {
            //ToDo code for the future
            return this;
        }

    }

}
