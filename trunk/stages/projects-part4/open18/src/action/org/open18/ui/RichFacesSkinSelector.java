package org.open18.ui;

import java.io.Serializable;

import java.util.Map;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.theme.ThemeSelector;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;

/**
 * <p>
 * The sole purpose of this component is to lookup the current RichFaces skin.
 * </p>
 * <p>
 * This class is used by Ajax4jsf to lookup the current RichFaces skin name. The
 * skin name is specified in the <code>richFacesSkin</code> property of the
 * active Seam theme. If a skin has not been set, then the default skin will be
 * used.
 * </p>
 * <p>
 * This component resides in session-scope since Ajax4jsf does some of its work
 * outside of the Seam context (even outside of Seam's servlet filter).
 * Therefore, the component must be in a scope that is accessible from outside
 * of Seam's container.
 * </p>
 * <p>
 * This component is referenced as an EL value binding by setting the servlet
 * context parameter <code>org.richfaces.SKIN</code> to
 * <code>#{richFacesSkinSelector.skin}</code>. In the order that lookup
 * events occur, this value expression will always be resolved by the Seam
 * container first. Therefore, it will be available in session scope by the time
 * the Ajax4jsf filter needs to do its work. The {@link ThemeSelector} is also a
 * session-scoped component, and hence will also be initialized at that point.
 * </p>
 * <p>
 * Of course, to utilize this component, it is necessary establish themes in the
 * Seam configuration.
 * </p>
 * 
 * @author dallen
 */
@Name("richFacesSkinSelector")
@Scope(ScopeType.SESSION)
public class RichFacesSkinSelector implements Serializable {

	private static final String RICH_FACES_SKIN_KEY = "richFacesSkin";
	private static final String RICH_FACES_DEFAULT_SKIN = "DEFAULT";
	
	private String defaultSkin = RICH_FACES_DEFAULT_SKIN;
	
	@In	private ThemeSelector themeSelector;
	@In private Map<String, String> theme;

	@Factory(value = "richFacesSkin", autoCreate = true, scope = ScopeType.EVENT)
	public String getSkin() {
		String skin = null;
		if (themeSelector.getTheme() != null) {
			if (theme.containsKey(RICH_FACES_SKIN_KEY)) {
				skin = theme.get(RICH_FACES_SKIN_KEY);
			}
			if (skin == null) {
				skin = themeSelector.getTheme().replaceFirst("_theme$", "");
				if (RICH_FACES_DEFAULT_SKIN.equalsIgnoreCase(skin)) {
					skin = RICH_FACES_DEFAULT_SKIN;
				}
			}
		}

		if (skin == null) {
			skin = defaultSkin;
		}

		return skin;
	}

	public String getDefaultSkin() {
		return defaultSkin;
	}

	public void setDefaultSkin(String defaultSkin) {
		this.defaultSkin = defaultSkin;
	}

}
