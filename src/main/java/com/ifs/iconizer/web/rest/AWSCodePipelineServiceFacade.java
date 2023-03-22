package com.ifs.iconizer.web.rest;

import java.util.UUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.codepipeline.AWSCodePipeline;
import com.amazonaws.services.codepipeline.AWSCodePipelineClient;
import com.amazonaws.services.codepipeline.AWSCodePipelineClientBuilder;
import com.amazonaws.services.codepipeline.model.CreatePipelineRequest;
import com.amazonaws.services.codepipeline.model.CreatePipelineResult;

public class AWSCodePipelineServiceFacade {
	
	void createCodeCommitRepo() {
		//CodeCommitRequest obj = new CodeCommitRequest();
		
		AWSCodePipeline obj = new AWSCodePipelineClient();
		/*AWSCredenti
		AWSStaticCredentials staticCredentials = new AWSStaticCredentialsProvider(credentials)
		AWSCredentialsProvider credentials = new AWSStaticCredentialsProvider(staticCredentials);
		AWSCodePipelineClientBuilder.standard().withCredentials(credentials);
		*/
		AWSCredentials credentials = new BasicAWSCredentials("","");
		AWSCodePipeline codePipeline = AWSCodePipelineClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
		CreatePipelineRequest createPipelineRequest = new CreatePipelineRequest();
		//createPipelineRequest.setPipeline(createPipelineRequest);
		codePipeline.createPipeline(createPipelineRequest);
				//(credentials)).build();
	}
	
	/*private String provisionPipeline() {
	    String pipelineId = config.getProperty(ConfigProps.TRANSCODE_PIPELINE);

	    if (pipelineId == null) {
	    	LOG.info("Provisioning ETS Pipeline.");
	        state = ProvisionState.PROVISIONING;
	        Notifications notifications = new Notifications()
	            .withError(config.getProperty(ConfigProps.TRANSCODE_TOPIC))
	            .withCompleted(config.getProperty(ConfigProps.TRANSCODE_TOPIC))
	            .withProgressing("")
	            .withWarning("");

	        CreatePipelineRequest pipelineRequest = new CreatePipelineRequest()
	            .withName("amm-reinvent-pipeline-" + UUID.randomUUID().toString().replace("-", "").substring(0, 18).toUpperCase())
	            .withRole(config.getProperty(ConfigProps.TRANSCODE_ROLE))
	            .withInputBucket(config.getProperty(ConfigProps.S3_UPLOAD_BUCKET))
	            .withOutputBucket(config.getProperty(ConfigProps.S3_UPLOAD_BUCKET))
	            .withNotifications(notifications);

	        try {
	            CreatePipelineResult pipelineResult = transcoderClient.createPipeline(pipelineRequest);
	            pipelineId = pipelineResult.getPipeline().getId();
	            LOG.info("Pipeline {} created. Persisting to configuration provider.", pipelineId);
	            config.getConfigurationProvider().persistNewProperty(ConfigProps.TRANSCODE_PIPELINE, pipelineId);
	        } catch (AmazonServiceException e) {
	            LOG.error("Failed creating pipeline {}", pipelineRequest.getName(), e);
	            state = ProvisionState.UNPROVISIONED;
	        }
	    }
	    return pipelineId;
	}*/
	 

}
