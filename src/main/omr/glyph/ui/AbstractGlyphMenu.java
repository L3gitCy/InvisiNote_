//----------------------------------------------------------------------------//
//                                                                            //
//                      A b s t r a c t G l y p h M e n u                     //
//                                                                            //
//----------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">                          //
//  Copyright © Hervé Bitteur and others 2000-2013. All rights reserved.      //
//  This software is released under the GNU General Public License.           //
//  Goto http://kenai.com/projects/audiveris to report bugs or suggestions.   //
//----------------------------------------------------------------------------//
// </editor-fold>
package omr.glyph.ui;

import omr.glyph.Nest;
import omr.glyph.facets.Glyph;

import omr.sheet.Sheet;

import omr.ui.util.SeparableMenu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * Abstract class {@code AbstractGlyphMenu} is the base for
 * glyph-based menus such as {@link SymbolMenu} and {@link
 * omr.sig.ui.PileMenu}.
 *
 * @author Hervé Bitteur
 */
public abstract class AbstractGlyphMenu
{
    //~ Static fields/initializers ---------------------------------------------

    /** Usual logger utility */
    private static final Logger logger = LoggerFactory.getLogger(
            AbstractGlyphMenu.class);

    //~ Instance fields --------------------------------------------------------
    /** Concrete menu. */
    protected final SeparableMenu menu = new SeparableMenu();

    /** Related sheet. */
    protected final Sheet sheet;

    /** Related nest. */
    protected final Nest nest;

    /** The selected glyphs. */
    protected Set<Glyph> glyphs;

    /** Current number of selected glyphs. */
    protected int glyphNb;

    /** To manage elaboration. */
    protected boolean initDone = false;

    //~ Constructors -----------------------------------------------------------
    //
    //-------------------//
    // AbstractGlyphMenu //
    //-------------------//
    /**
     * Creates a new AbstractGlyphMenu object.
     *
     * @param sheet     the related sheet
     * @param text      the menu text
     * @param menuClass the precise class to use for menu
     */
    public AbstractGlyphMenu (Sheet sheet,
                              String text)
    {
        this.sheet = sheet;
        nest = sheet.getNest();
        menu.setText(text);
    }

    //~ Methods ----------------------------------------------------------------
    //---------//
    // getMenu //
    //---------//
    /**
     * Report the concrete menu.
     *
     * @return the usable menu
     */
    public SeparableMenu getMenu ()
    {
        return menu;
    }

    //------------//
    // updateMenu //
    //------------//
    /**
     * Update the menu according to the currently selected glyphs.
     *
     * @return the number of selected glyphs
     */
    public int updateMenu (Set<Glyph> glyphs)
    {
        if (!initDone) {
            initMenu();
            initDone = true;
        }
        
        this.glyphs = glyphs;

        // Analyze the context
        glyphNb = (glyphs != null) ? glyphs.size() : 0;

        // Update the menu root item
        menu.setEnabled(glyphNb > 0);

        return glyphNb;
    }

    //----------//
    // initMenu //
    //----------//
    /**
     * Initialize the menu instance, once for all.
     * Further updates will be implemented through updateMenu() method.
     */
    protected void initMenu ()
    {
    }
}