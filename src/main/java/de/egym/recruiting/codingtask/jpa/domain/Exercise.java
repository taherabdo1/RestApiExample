package de.egym.recruiting.codingtask.jpa.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Cacheable(value = false)
@Entity
public class Exercise extends AbstractEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		// type enum
		public enum Type {
			RUNNING, CYCLING, SWIMMING, ROWING, WALKING, CIRCUIT_TRAINING, STRENGTH_TRAINING, FITNESS_COURSE, SPORTS, OTHER;
			public String value() {
				return name();
			}

			public static Type fromValue(String v) {
				return valueOf(v);
			}

		}

		@JsonBackReference
		@ManyToOne
		User user;
		
		@Column(nullable = false)
		Type excerciseType;

		@Temporal(TemporalType.TIMESTAMP)
		@Column(nullable = false)
		@ApiModelProperty(required = true, value = "Date in ISO format, i.e., yyyy-MM-dd:hh:mm:ss.", example = "1987-06-15:15:30:00")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd:hh:mm:ss", timezone = "GMT")
		Date start;
		
		@Column(nullable = false)
		long duration;

		@Column(nullable = false)
		int calories;

		@Column(nullable = true)
		double distance;

		public Type getExcerciseType() {
			return excerciseType;
		}

		public void setExcerciseType(Type excerciseType) {
			this.excerciseType = excerciseType;
		}

		public Date getStart() {
			return start;
		}

		public void setStart(Date start) {
			this.start = start;
		}

		public long getDuration() {
			return duration;
		}

		public void setDuration(long duration) {
			this.duration = duration;
		}

		public int getCalories() {
			return calories;
		}

		public void setCalories(int calories) {
			this.calories = calories;
		}

		public double getDistance() {
			return distance;
		}

		public void setDistance(double distance) {
			this.distance = distance;
		}

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

}
