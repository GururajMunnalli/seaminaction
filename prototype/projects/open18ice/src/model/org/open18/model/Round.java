package org.open18.model;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.DATE;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.hibernate.validator.Past;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Role;

@Entity
@Table(name = "ROUND")
//@Name("newRound")
//@Role(name = "roundExample")
public class Round implements java.io.Serializable {

	private Long id;
	private Date date;
	private String notes;
	private Golfer golfer;
	private Set<Score> scores = new LinkedHashSet<Score>(0);
	private TeeSet teeSet;
	private Integer totalScore;
	private String weather;
	
	private Date afterDateFilter;
	private Date beforeDateFilter;
	private List<String> weatherFilter;
	private List<Course> courseFilter;
	private Integer lowScoreFilter;
	private Integer highScoreFilter;

	public Round() {}

	public Round(Long id, Date date, String notes, Golfer golfer,
		Set<Score> scores, TeeSet teeSet, Integer totalScore,
		String weather) {
		this.id = id;
		this.date = date;
		this.notes = notes;
		this.golfer = golfer;
		this.scores = scores;
		this.teeSet = teeSet;
		this.totalScore = totalScore;
		this.weather = weather;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	//@Past
	@Temporal(DATE)
	@Column(name = "DATE", length = 8)
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Notes about the round. Perhaps a ranking in a tournament,
	 * something odd about the course, or mental reminders or lessons
	 * learned.
	 */
	@Lob
	@Length(max = 50000)
	@Column(name = "NOTES")
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String comment) {
		this.notes = comment;
	}

	@NotNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "GOLFER_ID", nullable = false)
	public Golfer getGolfer() {
		return golfer;
	}

	public void setGolfer(Golfer golfer) {
		this.golfer = golfer;
	}

	@OneToMany(cascade = ALL, fetch = LAZY, mappedBy = "round")
	public Set<Score> getScores() {
		return scores;
	}

	public void setScores(Set<Score> scores) {
		this.scores = scores;
	}

	@NotNull
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "TEE_SET_ID", nullable = false)
	public TeeSet getTeeSet() {
		return teeSet;
	}

	public void setTeeSet(TeeSet teeSet) {
		this.teeSet = teeSet;
	}

	/**
	 * A calculated value stored for convenience when displaying
	 * rounds.
	 */
	@NotNull
	@Column(name = "TOTAL_SCORE")
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public void updateTotalScore() {
		this.totalScore = calculateTotalScore();
	}

	@NotNull
	@Length(max = 25)
	@Column(name = "WEATHER", nullable = false, length = 25)
	public String getWeather() {
		return this.weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	@Transient
	public Date getAfterDateFilter() {
		return afterDateFilter;
	}

	public void setAfterDateFilter(Date afterDateFilter) {
		this.afterDateFilter = afterDateFilter;
	}

	@Transient
	public Date getBeforeDateFilter() {
		return beforeDateFilter;
	}

	public void setBeforeDateFilter(Date beforeDateFilter) {
		this.beforeDateFilter = beforeDateFilter;
	}

	@Transient
	public List<String> getWeatherFilter() {
		return weatherFilter;
	}

	public void setWeatherFilter(List<String> weatherFilter) {
		this.weatherFilter = weatherFilter;
	}

	@Transient
	public List<Course> getCourseFilter() {
		return courseFilter;
	}

	public void setCourseFilter(List<Course> courseFilter) {
		this.courseFilter = courseFilter;
	}

	@Transient
	public Integer getLowScoreFilter() {
		return lowScoreFilter;
	}

	public void setLowScoreFilter(Integer lowScoreFilter) {
		this.lowScoreFilter = lowScoreFilter;
	}

	@Transient
	public Integer getHighScoreFilter() {
		return highScoreFilter;
	}

	public void setHighScoreFilter(Integer highScoreFilter) {
		this.highScoreFilter = highScoreFilter;
	}

	protected Integer calculateTotalScore() {
		Integer total = null;
		for (Score score : scores) {
			if (score.getStrokes() != null) {
				if (total == null) {
					total = 0;
				}
				total += score.getStrokes();
			}
		}

		return total;
	}

	@javax.persistence.Transient
	public double getAveragePutts() {
		double total = 0;
		int num = 0;
		
		for (Score score : getScores()) {
		    if (score.getPutts() != null) {
		        total += score.getPutts();
		        num++;
		    }
		}

		return num > 0 ? total / num : 0;
	}

	@javax.persistence.Transient
	public int getStrokesOverPar() {
		int total = 0;

		for (Score score : getScores()) {
			total += (score.getStrokes() - score.getHole().getPar(getGolfer().getGender()));
		}

		return total;
	}
}
