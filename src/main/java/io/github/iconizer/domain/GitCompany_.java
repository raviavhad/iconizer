package io.github.iconizer.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(GitCompany.class)
public abstract class GitCompany_ {

	public static volatile ListAttribute<GitCompany, String> gitProjects;
	public static volatile SingularAttribute<GitCompany, String> gitProvider;
	public static volatile SingularAttribute<GitCompany, String> name;
	public static volatile SingularAttribute<GitCompany, Long> id;
	public static volatile SingularAttribute<GitCompany, User> user;

}

