package io.github.iconizer.domain;

import java.time.Instant;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(SubGenEvent.class)
public abstract class SubGenEvent_ {

	public static volatile SingularAttribute<SubGenEvent, Instant> date;
	public static volatile SingularAttribute<SubGenEvent, GeneratorIdentity> owner;
	public static volatile SingularAttribute<SubGenEvent, Integer> week;
	public static volatile SingularAttribute<SubGenEvent, Integer> month;
	public static volatile SingularAttribute<SubGenEvent, Integer> hour;
	public static volatile SingularAttribute<SubGenEvent, Integer> year;
	public static volatile SingularAttribute<SubGenEvent, Long> id;
	public static volatile SingularAttribute<SubGenEvent, String> source;
	public static volatile SingularAttribute<SubGenEvent, String> type;
	public static volatile SingularAttribute<SubGenEvent, String> event;
	public static volatile SingularAttribute<SubGenEvent, Integer> day;

}

