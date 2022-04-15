package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.junit.Assert;
import pojo.request.CreateIssue;
import pojo.request.CreateProject;
import pojo.request.IssueTimeEstimate;
import pojo.request.MoveIssue;
import resources.APIResources;
import resources.Constants;
import resources.RestApiClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static resources.Utils.getJsonPath;

public class StepDefinition {
    private String endpointUrl;
    private Response response;
    CreateProject createProject = new CreateProject();
    CreateIssue createIssue = new CreateIssue();
    MoveIssue moveIssue = new MoveIssue();
    IssueTimeEstimate issueTimeEstimate = new IssueTimeEstimate();
    static ThreadLocal<Map<String, String>> threadLocal = new ThreadLocal<>();
    RestApiClient restApiClient = new RestApiClient();

    static {
        threadLocal.set(new HashMap<>());
    }

    @Given("Set project name {string} with random time in the payload")
    public void setNameInThePayloadWithRandomTime(String projectName) {
        createProject.setName(projectName + "-" + new Date().getTime());
    }

    @When("User call the create project Api")
    public void userCallTheCreateProjectApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource();
        response = restApiClient.post(endpointUrl, createProject);
        String projectId = getJsonPath(response, "id");
        threadLocal.get().put("projectId", projectId);

    }


    @When("User call the list projects Api")
    public void userCallTheProjectsApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource();
        response = restApiClient.get(endpointUrl);

    }

    @And("The response received")
    public void theResponseReceived() {
    }


    @Then("The response code should be {int}")
    public void theResponseCodeShouldBe(int statusCode) {
        Assert.assertEquals(statusCode, response.getStatusCode());
    }

    @And("The issue type should be {string}")
    public void theIssueTypeShouldBe(String issueType) {
        String acutalType = getJsonPath(response, "type[0]");//
        Assert.assertEquals(issueType, acutalType);
    }

    @And("The project name should start with {string}")
    public void theProjectNameShouldStartWith(String expectedName) {
        String actualName = getJsonPath(response, "name");
        Assert.assertTrue(actualName.startsWith(expectedName));

    }
    

    @And("Set issue title {string} with random time in the payload")
    public void setIssueTitleInThePayload(String issueTitle) {
        createIssue.setTitle(issueTitle + "-" + new Date().getTime());

    }

    @And("The issue title should start with {string}")
    public void theIssueTitleShouldStartWith(String issueTitle) {
        String actualIssueTitle = getJsonPath(response, "title");
        Assert.assertTrue(actualIssueTitle.startsWith(issueTitle));
    }

    @And("Set the issue title {string} in the payload")
    public void setTheIssueTitleInThePayload(String expectedIssueTitle) {
        createIssue.setTitle(expectedIssueTitle);

    }

    @Given("Set to_project_id {string} in the payload")
    public void setTo_project_idInThePayload(String ProjectId) {
        moveIssue.setTo_project_id(ProjectId);
    }
    

    @Given("Set the duration {string} in the payload")
    public void setTheDurationInThePayload(String duration) {
        issueTimeEstimate.setDuration(duration);

    }


    @When("User call the Create issue Api")
    public void userCallTheCreateIssueApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource() + threadLocal.get().get("projectId") + "/issues";
        response = restApiClient.post(endpointUrl, createIssue);
        String issueId = getJsonPath(response, "iid");
        threadLocal.get().put("issueId", issueId);
    }
    
    @When("User call the list issues of project Api")
    public void userCallTheGetIssuesApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource() + threadLocal.get().get("projectId") + "/issues/";
        response = restApiClient.get(endpointUrl);
    }

    @When("User call edit issue Api")
    public void userCallEditIssueApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource() + threadLocal.get().get("projectId") + "/issues/" + threadLocal.get().get("issueId");
        response = restApiClient.put(endpointUrl, createIssue);
    }

    @When("User call delete issue Api")
    public void userCallDeleteIssueApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource() + threadLocal.get().get("projectId") + "/issues/" + threadLocal.get().get("issueId");
        response = restApiClient.delete(endpointUrl);
    }

    @When("User call move issue project Api")
    public void userCallMoveIssueProjectApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource() + threadLocal.get().get("projectId") + "/issues/" + threadLocal.get().get("issueId") + "/move";
        response = restApiClient.post(endpointUrl, moveIssue);
    }

    @When("User call set issue estimate time Api")
    public void userCallSetIssueEstimateTimeApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource() + threadLocal.get().get("projectId") + "/issues/" + threadLocal.get().get("issueId") + "/time_estimate";
        response = restApiClient.post(endpointUrl, issueTimeEstimate);
    }

    @When("User call clone issue Api")
    public void userCallCloneIssueApi() {
        endpointUrl = Constants.BASE_URL + APIResources.userPath.getResource() + threadLocal.get().get("projectId") + "/issues/" + threadLocal.get().get("issueId") + "/clone";
        response = restApiClient.post(endpointUrl, moveIssue);
    }
    

    @And("The updatedAt is valid")
    public void theUpdatedAtIsValid() {
        String updateAt = getJsonPath(response, "updated_at");
        Assert.assertNotNull(updateAt);
        Assert.assertNotNull(LocalDateTime.parse(updateAt, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
    }

    @And("Time estimate should be {string}")
    public void timeEstimateShouldBe(String expectedDuration) {
        String actualDuration= getJsonPath(response,"human_time_estimate");
        Assert.assertEquals(actualDuration,expectedDuration);
    }

    @Given("User provide token in the header")
    public void userProvideTokenInTheHeader() {
        
    }

    @And("The to project id should be {string}")
    public void theToProjectIdShouldBe(String arg0) {
    }
}