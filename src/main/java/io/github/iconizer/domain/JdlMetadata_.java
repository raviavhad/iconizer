package io.github.iconizer.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(JdlMetadata.class)
public abstract class JdlMetadata_ {

	public static volatile SingularAttribute<JdlMetadata, Instant> createdDate;
	public static volatile SingularAttribute<JdlMetadata, String> name;
	public static volatile SingularAttribute<JdlMetadata, Boolean> isPublic;
	public static volatile SingularAttribute<JdlMetadata, String> id;
	public static volatile SingularAttribute<JdlMetadata, Instant> updatedDate;
	public static volatile SingularAttribute<JdlMetadata, User> user;

}

