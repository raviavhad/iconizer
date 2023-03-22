/**
 * Copyright 2017-2020 the original author or authors from the JHipster Online project.
 *
 * This file is part of the JHipster Online project, see https://github.com/jhipster/jhipster-online
 * for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.iconizer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Jhonline.
 * <p>
 * Properties are configured in the application.yml file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private JhipsterCmd jhipsterCmd = new JhipsterCmd();

    private final Github github = new Github();

    private final Gitlab gitlab = new Gitlab();

    private final Mail mail = new Mail();
    
    private String tmpFolder = "/tmp";
    
    private MortgageAppType mortgage1 = new MortgageAppType();
    
    private MortgageAppType mortgage2 = new MortgageAppType();
    
    
    
    public MortgageAppType getMortgage1() {
		return mortgage1;
	}

	public void setMortgage1(MortgageAppType mortgage1) {
		this.mortgage1 = mortgage1;
	}

	public MortgageAppType getMortgage2() {
		return mortgage2;
	}

	public void setMortgage2(MortgageAppType mortgage2) {
		this.mortgage2 = mortgage2;
	}

	public JhipsterCmd getJhipsterCmd() {
        return jhipsterCmd;
    }

    public void setJhipsterCmd(JhipsterCmd jhipsterCmd) {
        this.jhipsterCmd = jhipsterCmd;
    }

    public String getTmpFolder() {
        return tmpFolder;
    }

    public void setTmpFolder(String tmpFolder) {
        this.tmpFolder = tmpFolder;
    }

    public Github getGithub() {
        return github;
    }

    public Gitlab getGitlab() {
        return gitlab;
    }

    public Mail getMail() {
        return mail;
    }

    public static class MortgageAppType {
        private App app = new App();

		public App getApp() {
			return app;
		}

		public void setApp(App app) {
			this.app = app;
		}
      
    }
    
    public static class App {
        private Gateway gateway = new Gateway();
        private Product product = new Product();
        private Service service = new Service();
        private String template = "";
        private String type = "";

		public Gateway getGateway() {
			return gateway;
		}

		public void setGateway(Gateway gateway) {
			this.gateway = gateway;
		}

		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		public Service getService() {
			return service;
		}

		public void setService(Service service) {
			this.service = service;
		}

		public String getTemplate() {
			return template;
		}

		public void setTemplate(String template) {
			this.template = template;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
		
		
    }
    
    public static class Service {
        private String key = "";
        private String value = "";
		
        public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
    }
    public static class Product {
        private String key = "";
        private String value = "";
		
        public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
    }
    
    
    public static class Gateway {
        private String key = "";
        private String value = "";
		
        public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
    }
    
    
    public static class JhipsterCmd {
        private String cmd = "jhipster";
        private Integer timeout = 120;

        public String getCmd() {
            return cmd;
        }

        public void setCmd(String cmd) {
            this.cmd = cmd;
        }

        public Integer getTimeout() {
            return timeout;
        }

        public void setTimeout(Integer timeout) {
            this.timeout = timeout;
        }
    }

    public static class Github {
        private String clientId;
        private String clientSecret;
        private String host = "https://github.com";
        private String jhipsterBotOauthToken = "";

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getJhipsterBotOauthToken() {
            return jhipsterBotOauthToken;
        }

        public void setJhipsterBotOauthToken(String jhipsterBotOauthToken) {
            this.jhipsterBotOauthToken = jhipsterBotOauthToken;
        }
    }

    public static class Gitlab {
        private String clientId;
        private String clientSecret;
        private String host = "https://gitlab.com";
        private String redirectUri;

        public String getClientId() {
            return clientId;
        }

        public void setClientId(String clientId) {
            this.clientId = clientId;
        }

        public String getClientSecret() {
            return clientSecret;
        }

        public void setClientSecret(String clientSecret) {
            this.clientSecret = clientSecret;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getRedirectUri() {
            return redirectUri;
        }

        public void setRedirectUri(String redirectUri) {
            this.redirectUri = redirectUri;
        }
    }

    public static class Mail {

        public boolean enable;

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public boolean isEnable() {
            return enable;
        }
    }

}
