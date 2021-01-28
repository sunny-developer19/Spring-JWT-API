package app.dto;

import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.PersistenceProperty;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

@Entity
public class Idea {
	
	
	@javax.persistence.Id
    @GeneratedValue
	private Long id;
	
	private String content;
	private int impact;
	private int ease;
	private int confidence;


	private double averageScore;	
	
	private long created_at;
	
	@PrePersist
	@PostPersist
	public void setCreatedAt()
	{
		Date date = new Date();
		created_at = date.getTime();
		
		this.averageScore = (double)(impact + confidence + ease) / 3;
		this.averageScore = Math.round(this.averageScore*100.0)/100.0;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getImpact() {
		return impact;
	}
	public void setImpact(int impact) {
		this.impact = impact;
	}
	public int getEase() {
		return ease;
	}
	public void setEase(int ease) {
		this.ease = ease;
	}
	public int getConfidence() {
		return confidence;
	}
	public void setConfidence(int confidence) {
		this.confidence = confidence;
	}
	public double getAverage_score() {
		this.averageScore = (double)(impact + confidence + ease) / 3;
		this.averageScore = Math.round(this.averageScore*100.0)/100.0;
		return averageScore;
	}
	public void setAverage_score(double average_score) {
		this.averageScore = (double)(impact + confidence + ease) / 3;
		this.averageScore = Math.round(this.averageScore*100.0)/100.0;
	}
	public long getCreatedAt() {
		return created_at;
	}
	public void setCreatedAt(long createdAt) {
		this.created_at = createdAt;
	}
	
	@PostLoad
	private void onLoad() {
		this.averageScore = (double)(impact + confidence + ease) / 3;
		this.averageScore = Math.round(this.averageScore*100.0)/100.0;
	}
	

}
