package com.ifs.iconizer.web.rest;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.google.common.io.Files;

import io.github.iconizer.config.ApplicationProperties;

public class IconizerHelper {

	@Autowired
	private ApplicationProperties applicationProperties;

	public static String replaceCharacter(char character1, char character2, String string) {
		char[] charArray = string.toCharArray();

		for (int i = 0; i < charArray.length; i++) {
			if (charArray[i] == character1) {
				charArray[i] = character2;
			}
		}

		System.out.println("new string =" + String.valueOf(charArray));
		return String.valueOf(charArray);
	}

	public static String generateECSCloudFormationTemplate(String ProjectName, String exportdir) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/cloudformation.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();

		context.put("ServerGroup", ProjectName + "ServerGroup");
		context.put("ProjectRepoName", ProjectName);
		context.put("CodeDeployAppName", ProjectName + "CodeDeployApp");
		context.put("DeploymentGroupName", ProjectName + "DeploymentGroup");
		context.put("ServerTag", "IconizerServer");
		context.put("BucketName", "iconizer");
		context.put("CodePipelineName", ProjectName + "CodePipeline");
		context.put("Region", "us-east-2");
		context.put("CodePipelineTag", ProjectName + "CodePipeline");
		context.put("DeploymentConfig", ProjectName + "DeploymentConfig");
		context.put("DeploymentConfig", ProjectName + "DeploymentConfig");
		context.put("SourceAction", ProjectName + "SourceAction");
		context.put("DeployAction", ProjectName + "DeployAction");
		context.put("AppSourceCode", ProjectName + "AppSourceCode");

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		// Creating Directory for the new project
		File file = new File(exportdir + ProjectName);
		// Creating the directory
		boolean bool = file.mkdir();
		System.out.println("File path" + file.getAbsolutePath());
		if (bool) {
			System.out.println("Project directory created successfully");
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		String cloudFormationTemplate = "";

		try {
			String outputFilePath = file.getCanonicalPath() + "\\CloudFormation.template";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			cloudFormationTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		return cloudFormationTemplate;
	}

	public static String generateCloudFormationTemplate(String ProjectName, String exportdir) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/cloudformation.vm");
		/* create a context and add data */

		VelocityContext context = new VelocityContext();

		context.put("ServerGroup", ProjectName + "ServerGroup");
		context.put("ProjectRepoName", ProjectName);
		context.put("CodeDeployAppName", ProjectName + "CodeDeployApp");
		context.put("DeploymentGroupName", ProjectName + "DeploymentGroup");
		context.put("ServerTag", "IconizerServer");
		context.put("BucketName", "iconizer");
		context.put("CodePipelineName", ProjectName + "CodePipeline");
		context.put("Region", "us-east-2");
		context.put("CodePipelineTag", ProjectName + "CodePipeline");
		context.put("DeploymentConfig", ProjectName + "DeploymentConfig");
		context.put("DeploymentConfig", ProjectName + "DeploymentConfig");
		context.put("SourceAction", ProjectName + "SourceAction");
		context.put("DeployAction", ProjectName + "DeployAction");
		context.put("AppSourceCode", ProjectName + "AppSourceCode");

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		// Creating Directory for the new project
		File file = new File(exportdir + ProjectName);
		// Creating the directory
		boolean bool = file.mkdir();
		System.out.println("File path" + file.getAbsolutePath());
		if (bool) {
			System.out.println("Project directory created successfully");
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		String cloudFormationTemplate = "";

		try {
			String outputFilePath = file.getCanonicalPath() + "\\CloudFormation.template";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			cloudFormationTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		return cloudFormationTemplate;
	}

	public static void generateAppspecTemplate(String ProjectName, String exportdir) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/appspec.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();

		context.put("Projectname", ProjectName);

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String appspecTemplate = "";

		try {
			String outputFilePath = exportdir + "\\" + ProjectName + "\\appspec.yml";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			appspecTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

	}

	public static String generateBuildspecTemplate(String ProjectName, String exportdir) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/buildspec.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();

		String ecrBaseLocation = "659418003263.dkr.ecr.us-east-2.amazonaws.com/";

		context.put("ProjectName", ProjectName);
		context.put("Region", "us-east-2");
		context.put("ProjectECRRepo", (ecrBaseLocation + ProjectName).toLowerCase());

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String buildspecTemplate = "";

		try {
			String outputFilePath = exportdir + "\\" + ProjectName + "\\buildspec.yml";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			buildspecTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}
		return buildspecTemplate;
	}

	public static String generateEcsServiceTemplate(String ProjectName, String exportdir, String port) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/cfn-service.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();

		String ecrBaseLocation = "659418003263.dkr.ecr.us-east-2.amazonaws.com/";

		context.put("ProjectName", ProjectName);
		context.put("ContainerPort", port);
		context.put("LogGroupName", ProjectName + "LogGroup");
		context.put("LogStreamApi", ProjectName + "-api");

		String ecsServiceCFScriptPath = exportdir + "\\" + ProjectName + "\\cfn";
		// Creating Directory for the new project
		File file = new File(ecsServiceCFScriptPath);
		// Creating the directory
		boolean bool = file.mkdir();
		System.out.println("File path" + file.getAbsolutePath());
		if (bool) {
			System.out.println("Project directory created successfully");
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String serviceScript = "";

		try {
			String outputFilePath = ecsServiceCFScriptPath + "\\service.yml";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			serviceScript = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		return serviceScript;

	}

	public static String generateCodePipelineTemplate(String ProjectName, String exportdir) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/cloudformation_ecs.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();

		String ecrBaseLocation = "659418003263.dkr.ecr.us-east-2.amazonaws.com/";

		context.put("ProjectName", ProjectName);
		context.put("Region", "us-east-2");
		context.put("ProjectECRRepo", ProjectName.toLowerCase());
		context.put("CodePipelineName", ProjectName + "CodePipeline");
		context.put("SourceArtifact", ProjectName + "SourceArtifact");
		context.put("BuildArtifact", ProjectName + "BuildArtifact");
		context.put("StackName", ProjectName + "ECSStack");

		String ecsServiceCFScriptPath = exportdir + "\\" + ProjectName;
		// Creating Directory for the new project
		File file = new File(ecsServiceCFScriptPath);
		// Creating the directory
		boolean bool = file.mkdir();
		System.out.println("File path" + file.getAbsolutePath());
		if (bool) {
			System.out.println("Project directory created successfully");
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String cloudFormationTemplate = "";

		try {
			String outputFilePath = file.getCanonicalPath() + "\\CloudFormation.template";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			cloudFormationTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		return cloudFormationTemplate;
	}

	public static String generateWorkspaceTemplate(String ProjectName, String exportdir) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/workspace.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();

		String ecrBaseLocation = "659418003263.dkr.ecr.us-east-2.amazonaws.com/";

		context.put("ProjectName", ProjectName);
		context.put("Region", "us-east-1");


		String ecsServiceCFScriptPath = exportdir + "\\" + ProjectName;
		// Creating Directory for the new project
		File file = new File(ecsServiceCFScriptPath);
		// Creating the directory
		boolean bool = file.mkdir();
		System.out.println("File path" + file.getAbsolutePath());
		if (bool) {
			System.out.println("Project directory created successfully");
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String cloudFormationTemplate = "";

		try {
			String outputFilePath = file.getCanonicalPath() + "\\CloudFormation.template";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			cloudFormationTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		return cloudFormationTemplate;
	}

	public static String generateCloud9WorkspaceTemplate(String Project1Name, String Project2Name, String Project3Name, String exportdir,String baseName) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/cloud9.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();


		context.put("Project1Name", Project1Name);
		context.put("Project2Name", Project2Name);
		context.put("Project3Name", Project3Name);


		String ecsServiceCFScriptPath = exportdir + "\\" + baseName;
		// Creating Directory for the new project
		File file = new File(ecsServiceCFScriptPath);
		// Creating the directory
		boolean bool = file.mkdir();
		System.out.println("File path" + file.getAbsolutePath());
		if (bool) {
			System.out.println("Project directory created successfully");
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String cloudFormationTemplate = "";

		try {
			String outputFilePath = file.getCanonicalPath() + "\\Cloud9template";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			cloudFormationTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		return cloudFormationTemplate;
	}

	
	public static String generateInstallationScriptTemplate(String ProjectName, String exportdir) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		/* next, get the Template */
		Template t = ve.getTemplate("templates/before_install.vm");
		/* create a context and add data */
		VelocityContext context = new VelocityContext();

		context.put("ProjectName", ProjectName);

		String installationScriptPath = exportdir + "\\" + ProjectName + "\\aws-cicd";
		// Creating Directory for the new project
		File file = new File(installationScriptPath);
		// Creating the directory
		boolean bool = file.mkdir();
		System.out.println("File path" + file.getAbsolutePath());
		if (bool) {
			System.out.println("Project directory created successfully");
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String beforeInstallTemplate = "";

		try {
			String outputFilePath = installationScriptPath + "\\before_install.sh";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			beforeInstallTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		t = ve.getTemplate("templates/after_install.vm");
		/* create a context and add data */
		context = new VelocityContext();

		context.put("Projectname", ProjectName);

		/* now render the template into a StringWriter */
		writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String afterInstallTemplate = "";

		try {
			String outputFilePath = installationScriptPath + "\\after_install.sh";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			afterInstallTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		t = ve.getTemplate("templates/app_start.vm");
		/* create a context and add data */
		context = new VelocityContext();

		context.put("Projectname", ProjectName);

		/* now render the template into a StringWriter */
		writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String appStartTemplate = "";

		try {
			String outputFilePath = installationScriptPath + "\\app_start.sh";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			appStartTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		t = ve.getTemplate("templates/app_stop.vm");
		/* create a context and add data */
		context = new VelocityContext();

		context.put("Projectname", ProjectName);

		/* now render the template into a StringWriter */
		writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String appStopTemplate = "";

		try {
			String outputFilePath = installationScriptPath + "\\app_stop.sh";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			appStopTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}

		return installationScriptPath;
	}

	public static void copyFile(String from, String to) throws IOException {
		Path src = Paths.get(from);
		Path dest = Paths.get(to);
		Files.copy(src.toFile(), dest.toFile());
	}

	public static void copyInstallationScripts(String from, String to) throws IOException {
		Path src = Paths.get(from);
		Path dest = Paths.get(to);
		Files.copy(src.toFile(), dest.toFile());
	}

	public static void triggerBatch(String templatepath, String projectName, String baseName) {

		try {
			String template = templatepath;
			// String("C:\\work\\workspace\\jhipster\\Iconizer\\projecttemplate");
			String exportdir = new String("C:\\work\\workspace\\jhipster\\IconizerExport\\");

			// Generates Cloudformation template CICD with EC2 Deployment
			// String cloudFormationTemplate =
			// generateCloudFormationTemplate(baseName, exportdir);

			// Generates Cloudformation template for with Serverless ECS Fargate
			// Deployment
			String cloudFormationTemplate = generateCodePipelineTemplate(baseName, exportdir);

			CloudFormationServiceFacade cloudFormationService = new CloudFormationServiceFacade();
			System.out.println("CloudFormationTemplate - \n" + cloudFormationTemplate);

			cloudFormationService.init();
			cloudFormationService.createCICDStack(baseName, cloudFormationTemplate);

			String buildSpec = generateBuildspecTemplate(baseName, exportdir);
			//To be moved to Properties/DB
			String defaultServerPort = "8080";
			System.out.println("BuildSpec - \n" + buildSpec);
			String serviceSpec = generateEcsServiceTemplate(baseName, exportdir,defaultServerPort);
			System.out.println("ServiceSpec - \n" + serviceSpec);

			System.out.println("App : " + baseName);
			System.out.println("Template path " + templatepath);
			System.out.println("exportdir " + exportdir);

			System.out.println("Trigerring batch to generate project");
			Runtime.getRuntime()
					.exec(new String[] { "cmd.exe", "/c",
							"start C:\\work\\git-projects\\jhipster-online\\projecttemplate\\ProjectGeneratorBatch.bat",
							baseName, template, exportdir });
			System.out.println("Batch initiated!!");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void initiateAWSCodePipeline(String templatepath, String baseName, String serverPort) {

		try {
			String template = templatepath;

			String exportdir = new String("C:\\work\\workspace\\jhipster\\IconizerExport\\");

			// Generates Cloudformation template for with Serverless ECS Fargate
			// Deployment
			String cloudFormationTemplate = generateCodePipelineTemplate(baseName, exportdir);

			CloudFormationServiceFacade cloudFormationService = new CloudFormationServiceFacade();
			System.out.println("CloudFormationTemplate - \n" + cloudFormationTemplate);

			String buildSpec = generateBuildspecTemplate(baseName, exportdir);
			System.out.println("BuildSpec - \n" + buildSpec);
			String serviceSpec = generateEcsServiceTemplate(baseName, exportdir, serverPort);
			System.out.println("ServiceSpec - \n" + serviceSpec);

			cloudFormationService.init();
			cloudFormationService.createCICDStack(baseName, cloudFormationTemplate);

			System.out.println("App : " + baseName);
			System.out.println("Template path " + templatepath);
			System.out.println("exportdir " + exportdir);

			System.out.println("Batch initiated!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void initiateAWSWorkspace(String templatepath, String baseName) {

		try {
			String template = templatepath;

			String exportdir = new String("C:\\work\\workspace\\jhipster\\IconizerExport\\");

			// Generates Cloudformation template for with Serverless ECS Fargate
			// Deployment
			String awsWorkspaceTemplate = generateWorkspaceTemplate(baseName, exportdir);

			CloudFormationServiceFacade cloudFormationService = new CloudFormationServiceFacade();
			System.out.println("AWSWorkspaceTemplate - \n" + awsWorkspaceTemplate);


			cloudFormationService.initUSEast1();
			cloudFormationService.createWorkspaceStack(baseName, awsWorkspaceTemplate);

			System.out.println("App : " + baseName);
			System.out.println("Template path " + templatepath);
			System.out.println("exportdir " + exportdir);

			System.out.println("Batch initiated!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void initiateAWSCloud9Workspace(String templatepath, String project1Name , String project2Name, String project3Name, String baseName) {

		try {
			String template = templatepath;

			String exportdir = new String("C:\\work\\workspace\\jhipster\\IconizerExport\\");

			String awsWorkspaceTemplate = generateCloud9WorkspaceTemplate(project1Name, project2Name, project3Name,exportdir,baseName);

			CloudFormationServiceFacade cloudFormationService = new CloudFormationServiceFacade();
			System.out.println("AWSCloud9Template - \n" + awsWorkspaceTemplate);


			cloudFormationService.initUSEast2();
			cloudFormationService.createWorkspaceStack(baseName, awsWorkspaceTemplate);

			System.out.println("App : " + baseName);
			System.out.println("Template path " + templatepath);
			System.out.println("exportdir " + exportdir);

			System.out.println("Batch initiated!!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

	public static void generateProjectGeneratorTemplate(String ProjectName, String exportdir,
			String applicationTemplateType, ApplicationProperties applicationProperties, String packageName) throws IOException {
		/* first, get and initialize an engine */
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();

		String templateName = "";
		String gatewayKey = "";
		
		String msApp1Key = "";
		String msApp2Key = "";
		String gatewayName = "";
		String msApp1 = "";
		String msApp2 = "";
		
		String gatewayPortKey = "GatewayPort";
		String msApp1PortKey = "MSApp1Port";
		String msApp2PortKey = "MSApp2Port";
		String gatewayAppPortValue = "8080";
		String msApp1PortValue = "8081";
		String msApp2PortValue = "8082";
		
		String gateway1Key = "";
		String gateway2Key = "";
		String gateway1Port = "8080";
		String gateway2Port = "8083";
		
		String gateway2Name = "";
		String gateway2PortKey = "";
		String gatewayApp2PortValue = "";
		
		
		if (applicationTemplateType.equalsIgnoreCase("nft")) {
			
			gatewayName = ProjectName+ "gatewayapp";
			msApp1 = ProjectName+ "productapp";
			msApp2 = ProjectName+ "serviceapp";
			gatewayKey = "GatewayName";
			msApp1Key =  "MSApp1";
			msApp2Key =  "MSApp2";			
			templateName = "mortgagebank1.vm"; 
			
		}
		
		if (applicationTemplateType.equalsIgnoreCase("cryptoTransfer")) {

			
			gatewayName = ProjectName+ "gatewayapp";
			msApp1 = ProjectName+ "productapp";
			msApp2 = ProjectName+ "serviceapp";
			gatewayKey = "GatewayName";
			msApp1Key =  "MSApp1";
			msApp2Key =  "MSApp2";	
			templateName = "mortgagebank2.vm";
		}
		
		if (applicationTemplateType.equalsIgnoreCase("tokenCreation")) {
				
				gatewayName = ProjectName+ "gatewayapp";
				msApp1 = ProjectName+ "productapp";
				msApp2 = ProjectName+ "serviceapp";
				gatewayKey = "GatewayName";
				msApp1Key =  "MSApp1";
				msApp2Key =  "MSApp2";	
				templateName = "mortgagebank3.vm";
			}
		
		if (applicationTemplateType.equalsIgnoreCase("voting")) {
			
			gatewayName = ProjectName+"schooladmingatewayapp";
			
			msApp1 = ProjectName+ "productapp";
			msApp2 = ProjectName+ "serviceapp";
			gatewayKey = "GatewayName";
			gateway2Key = "Gateway2Name";
			msApp1Key =  "MSApp1";
			msApp2Key =  "MSApp2";	
			templateName = "voting.vm";
			gateway2Name = ProjectName+"parentsconnectgatewayapp";
			gateway2PortKey = "Gateway2Port";
			gatewayApp2PortValue = "8083";
			
			
		}
	
		/* next, get the Template */
		Template t = ve.getTemplate("templates/" + templateName);
		/* create a context and add data */
		VelocityContext context = new VelocityContext();

		context.put(gatewayKey, gatewayName);
		context.put(msApp1Key, msApp1);
		context.put(msApp2Key, msApp2);
		context.put(gatewayPortKey, gatewayAppPortValue);
		context.put(msApp1PortKey, msApp1PortValue);
		context.put(msApp2PortKey, msApp2PortValue);
		context.put("PackageName", packageName);
		
		if (applicationTemplateType.equalsIgnoreCase("voting")) {
		context.put(gateway2Key, gateway2Name);
		context.put(gateway2PortKey, gatewayApp2PortValue);
		}

		/* now render the template into a StringWriter */
		StringWriter writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		// Creating Directory for the new project
		File file = new File(exportdir + ProjectName);
		// Creating the directory
		boolean bool = file.mkdir();
		System.out.println("File path" + file.getAbsolutePath());
		if (bool) {
			System.out.println("Project directory created successfully");
		} else {
			System.out.println("Sorry couldn’t create specified directory");
		}

		/* now render the template into a StringWriter */
		writer = new StringWriter();
		t.merge(context, writer);
		/* show the World */
		System.out.println(writer.toString());

		String appspecTemplate = "";

		try {
			String outputFilePath = exportdir + "\\" + ProjectName + "\\app.jdl";
			FileWriter myWriter = new FileWriter(outputFilePath);
			myWriter.write(writer.toString());
			appspecTemplate = writer.toString();
			myWriter.close();
			System.out.println("Successfully wrote to the file.");
		} catch (IOException ex) {
			System.out.println("An error occurred.");
			ex.printStackTrace();
			throw ex;
		}
	}

	public static void triggerTemplateApplicationBatch(String templatepath, ApplicationProperties applicationProperties,
			String projectName, String baseName, String applicationTemplateType, String packageName) {

		//TODO : To be moved to properties/DB
		
		String gatewayName = "";
		String msApp1 = "";
		String msApp2 = "";
		String gateway1Name = "";
		String gateway2Name = "";

		if (applicationTemplateType.equalsIgnoreCase("nft")) {
			/*gatewayName = applicationProperties.getMortgage1().getApp().getGateway().getValue();
			msApp1 = applicationProperties.getMortgage1().getApp().getProduct().getValue();
			msApp2 = applicationProperties.getMortgage1().getApp().getService().getValue();*/
			gatewayName = baseName+ "gatewayapp";
			msApp1 = baseName+ "productapp";
			msApp2 = baseName+ "serviceapp";
		}
		
		if (applicationTemplateType.equalsIgnoreCase("cryptoTransfer")) {
			/*gatewayName = baseName+applicationProperties.getMortgage2().getApp().getGateway().getValue();
			msApp1 = baseName+applicationProperties.getMortgage2().getApp().getProduct().getValue();
			msApp2 = baseName+applicationProperties.getMortgage2().getApp().getService().getValue();*/
			
			gatewayName = baseName+ "gatewayapp";
			msApp1 = baseName+ "productapp";
			msApp2 = baseName+ "serviceapp";
		}
		
		if (applicationTemplateType.equalsIgnoreCase("tokenCreation")) {
			/*gatewayName = baseName+applicationProperties.getMortgage2().getApp().getGateway().getValue();
			msApp1 = baseName+applicationProperties.getMortgage2().getApp().getProduct().getValue();
			msApp2 = baseName+applicationProperties.getMortgage2().getApp().getService().getValue();*/
			
			gatewayName = baseName+ "gatewayapp";
			msApp1 = baseName+ "productapp";
			msApp2 = baseName+ "serviceapp";
		}

		
		if (applicationTemplateType.equalsIgnoreCase("voting")) {
			/*gatewayName = baseName+applicationProperties.getMortgage2().getApp().getGateway().getValue();
			msApp1 = baseName+applicationProperties.getMortgage2().getApp().getProduct().getValue();
			msApp2 = baseName+applicationProperties.getMortgage2().getApp().getService().getValue();*/
			
			gatewayName = baseName+"schooladmingatewayapp";
			msApp1 = baseName+ "productapp";
			msApp2 = baseName+ "serviceapp";
			
			gateway2Name = baseName+"parentsconnectgatewayapp";
			String gateway2Port = "8083"; 
			String exportdir = new String("C:\\work\\workspace\\jhipster\\IconizerExport\\");
			initiateAWSCodePipeline(exportdir+"\\baseName", gateway2Name, gateway2Port);
		}
		
		String exportdir = new String("C:\\work\\workspace\\jhipster\\IconizerExport\\");
		 
		//TODO : To be moved to properties/DB
		
		String gatewayAppPortValue = "8080";
		String msApp1PortValue = "8081";
		String msApp2PortValue = "8082"; 
		
		initiateAWSCodePipeline(exportdir+"\\baseName", gatewayName, gatewayAppPortValue);
		initiateAWSCodePipeline(exportdir+"\\baseName", msApp1, msApp1PortValue);
		initiateAWSCodePipeline(exportdir+"\\baseName", msApp2, msApp2PortValue);
		
		 

		try {
			generateProjectGeneratorTemplate(baseName, exportdir, applicationTemplateType, applicationProperties, packageName);
			/*Runtime.getRuntime()
					.exec(new String[] { "cmd.exe", "/c",
							"start C:\\work\\git-projects\\jhipster-online\\projecttemplate\\ProjectGeneratorTemplateBatch.bat",
							baseName, exportdir, gatewayName, msApp1, msApp2 });
			*/
			Runtime.getRuntime()
			.exec(new String[] { "cmd.exe", "/c",
					"start C:\\work\\git-projects\\jhipster-online\\projecttemplate\\ProjectGeneratorTemplateBatch.bat",
					baseName, exportdir, gatewayName, msApp1, msApp2, gateway2Name });
			
			System.out.println("Batch initiated!!");
			//initiateAWSCloud9Workspace(templatepath,gatewayName, msApp1,msApp2 ,baseName);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
