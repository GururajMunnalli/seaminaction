package org.open18.model;

import java.util.Date;
import javax.persistence.*;
import org.hibernate.validator.*;
import org.jboss.seam.annotations.*;
import org.jboss.seam.ScopeType;

@Entity
@PrimaryKeyJoinColumn(name="MEMBER_ID")
@Table(name = "GOLFER")
@Name("golferEntity")
@Scope(ScopeType.EVENT)
@Roles({
    @Role(name = "newGolfer", scope = ScopeType.EVENT),
    @Role(name = "golferExample", scope = ScopeType.EVENT)
})
public class Golfer extends Member {
    // class body remains unchanged
}
