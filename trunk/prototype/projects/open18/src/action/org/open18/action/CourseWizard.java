package org.open18.action;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Begin;
import org.jboss.seam.annotations.Conversational;
import org.jboss.seam.annotations.End;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.web.RequestParameter;
import org.jboss.seam.faces.FacesMessages;
import org.open18.model.Course;
import org.open18.model.Hole;

/**
 * Screen #1: select facility (opportunity for subflow here)
 * Screen #2: basic info (name, designer, golf pro, grass types, year built, num holes, num tee sets, has ladies par?)
 * Screen #3: description (nice big text area)
 * Screen #3: hole data (men's par, ladies' par, men's handicap, ladies' handicap, drop down for signature hole)
 * Screen #4: tee set info (name, color, ladies' slope/course, men's slope/course, position) | repeat until
 * Screen #5: tee distances for tee set                                                      | done
 * Screen #6: course photo
 */
@Name("courseWizard")
@Scope(ScopeType.CONVERSATION)
public class CourseWizard implements Serializable {
    
    @In
    private EntityManager entityManager;
    
    @In
    private FacesMessages facesMessages;
    
    @In(required = false)
    @Out(required = false) // this isn't working
    private FacilityHome facilityHome;

    @RequestParameter // only way the select facility will work
    private Long facilityId;
    
    @Out
    private Course newCourse;
    
    @Out(required = false)
    private List<Hole> holes;
    
    @Out(required = false)
    private String gender;
    
    private boolean ladiesParUnique = false;
    
    private boolean ladiesHandicapUnique = false;

    @Begin(pageflow = "Course Wizard")
    public String addCourse() {
        newCourse = new Course();
        newCourse.setFacility(facilityHome.getDefinedInstance());
        newCourse.setNumHoles(18);
        //facilityHome = null; // cleanup
        return newCourse.getFacility() != null ? "facilitySelected" : "facilityNotSelected";
    }

    public void assignFacility() {
        facilityHome.setFacilityId(facilityId);
        newCourse.setFacility(facilityHome.getInstance());
        //facilityHome = null; // cleanup
    }
    
    public void prepareHoleData() {
        holes = new ArrayList<Hole>();
        for (int i = 1; i <= newCourse.getNumHoles(); i++) {
            Hole hole = new Hole();
            hole.setNumber(i);
            hole.setCourse(newCourse);
            holes.add(hole);
        }
        
        gender = "Men";
    }
    
    public String submitMensHoleData() {
        for (Hole hole : holes) {
            hole.setLadiesPar(hole.getMensPar());
            if (!ladiesHandicapUnique) {
                hole.setLadiesHandicap(hole.getMensHandicap());
            }
        }
        
        if (ladiesParUnique || ladiesHandicapUnique) {
            gender = "Ladies";
        }
        else {
            gender = null;
            newCourse.setHoles(new HashSet<Hole>(holes));
            holes = null;
        }
        
        return gender;
    }

    public boolean isLadiesHoleDataRequired() {
        return ladiesParUnique || ladiesHandicapUnique;
    }
    
    public void submitLadiesHoleData() {
        gender = null;
        newCourse.setHoles(new HashSet<Hole>(holes));
        holes = null;
    }
    
    @End
    @Conversational
    public String save() {
        try {
            entityManager.persist(newCourse);
            facesMessages.add("#{newCourse.name} has been added to the course directory.");
            return "success";
        }
        catch (Exception e) {
            facesMessages.add("The course could not be saved.");
            // returning null indicates failure and will not end the conversation
            return null;
            //return "failure";
        }
    }

    public List<SelectItem> getParOptions() {
        List<SelectItem> options = new ArrayList<SelectItem>();
        for (int i = 2; i <= 6; i++) {
            options.add(new SelectItem(i));
        }
        return options;
    }
    
    public List<SelectItem> getHandicapOptions() {
        List<SelectItem> options = new ArrayList<SelectItem>();
        for (int i = 1; i <= 18; i++) {
            options.add(new SelectItem(i));
        }
        return options;
    }

    public boolean isLadiesParUnique() {
        return ladiesParUnique;
    }

    public void setLadiesParUnique(boolean ladiesParUnique) {
        this.ladiesParUnique = ladiesParUnique;
    }

    public boolean isLadiesHandicapUnique() {
        return ladiesHandicapUnique;
    }

    public void setLadiesHandicapUnique(boolean ladiesHandicapUnique) {
        this.ladiesHandicapUnique = ladiesHandicapUnique;
    }
}
