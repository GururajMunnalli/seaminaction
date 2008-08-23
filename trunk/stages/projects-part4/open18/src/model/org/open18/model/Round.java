package org.open18.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;

import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import org.hibernate.validator.Length;
import org.hibernate.validator.NotNull;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Restrict;
import org.open18.model.enums.Weather;

@Entity
@Table(name = "ROUND")
@Name("round")
public class Round implements Serializable {

	private Long id;
	private Integer version;
	private Date date;
	private String notes;
	private Golfer golfer;
	private TeeSet teeSet;
	private Integer totalScore;
	private Weather weather;
	private boolean selected;
	private Set<Score> scores = new LinkedHashSet<Score>(0);

	@Id @GeneratedValue
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Version
	public Integer getVersion() {
		return version;
	}

	private void setVersion(Integer version) {
		this.version = version;
	}
	
	@Temporal(TemporalType.DATE)
	@NotNull
	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Lob
	@Length(max = 50000)
	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String comment) {
		this.notes = comment;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GOLFER_ID", nullable = false)
	@NotNull
	public Golfer getGolfer() {
		return golfer;
	}

	public void setGolfer(Golfer golfer) {
		this.golfer = golfer;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEE_SET_ID", nullable = false)
	@NotNull
	public TeeSet getTeeSet() {
		return teeSet;
	}

	public void setTeeSet(TeeSet teeSet) {
		this.teeSet = teeSet;
	}

	@Column(name = "TOTAL_SCORE")
	@NotNull
	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	@Enumerated(EnumType.STRING)
	@NotNull
	public Weather getWeather() {
		return this.weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "round")
	public Set<Score> getScores() {
		return scores;
	}

	public void setScores(Set<Score> scores) {
		this.scores = scores;
	}

	@Transient
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@PreUpdate @PreRemove
	@Restrict
	public void restrict() {}
	
	@Transient
	public boolean isScoreDetailPresent() {
		return getScores().size() != 0;
	}
	
	@Transient
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

    @Transient
    public int getStrokesOverPar() {
        int total = 0;

        for (Score score : getScores()) {
            total += (score.getStrokes() - score.getHole().getPar(getGolfer().getGender()));
        }

        return total;
    }
	
	public double getAverageScore(long par) {
		double total = 0;
		int num = 0;

		for (Score score : getScores()) {
			if (score.getHole().getPar(getGolfer().getGender()) == par) {
				total += score.getStrokes();
				num++;
			}
		}

		return num > 0 ? total / num : 0;
	}
	
	@Transient
	public List<PuttFrequency> getPuttFrequencies() {
		Map<Integer, PuttFrequency> freqs = new HashMap<Integer, PuttFrequency>();
		for (Score score : getScores()) {
			if (score.getPutts() != null) {
				PuttFrequency freq;
				if (freqs.containsKey(score.getPutts())) {
					freq = freqs.get(score.getPutts());
				}
				else {
					freq = new PuttFrequency(score.getPutts());
					freqs.put(score.getPutts(), freq);
				}
				freq.increment();
			}
		}
		return new ArrayList<PuttFrequency>(freqs.values());
	}
	
	public class PuttFrequency implements Comparable<PuttFrequency> {
		private final int numPutts;
		private int count = 0;
		public PuttFrequency(int numPutts) {
			if (numPutts < 0) throw new IllegalArgumentException("Number of putts must be positive.");
			this.numPutts = numPutts;
		}
		
		public int getCount() {
			return count;
		}
		
		public int getNumPutts() {
			return numPutts;
		}
		
		public void increment() {
			count++;
		}
		
		@Override
		public int hashCode() {
			return numPutts;
		}
		
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof PuttFrequency)) return false;
			return this.numPutts == ((PuttFrequency) o).numPutts;
		}

		public int compareTo(PuttFrequency b) {
			return Integer.valueOf(numPutts).compareTo(Integer.valueOf(b.numPutts));
		}
	}

}
