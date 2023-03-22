package io.github.iconizer.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(EntityStats.class)
public abstract class EntityStats_ {

	public static volatile SingularAttribute<EntityStats, Instant> date;
	public static volatile SingularAttribute<EntityStats, GeneratorIdentity> owner;
	public static volatile SingularAttribute<EntityStats, Integer> week;
	public static volatile SingularAttribute<EntityStats, String> pagination;
	public static volatile SingularAttribute<EntityStats, Integer> year;
	public static volatile SingularAttribute<EntityStats, Integer> relationships;
	public static volatile SingularAttribute<EntityStats, Integer> month;
	public static volatile SingularAttribute<EntityStats, Integer> hour;
	public static volatile SingularAttribute<EntityStats, Boolean> fluentMethods;
	public static volatile SingularAttribute<EntityStats, String> service;
	public static volatile SingularAttribute<EntityStats, Long> id;
	public static volatile SingularAttribute<EntityStats, Integer> fields;
	public static volatile SingularAttribute<EntityStats, Integer> day;
	public static volatile SingularAttribute<EntityStats, String> dto;

}

