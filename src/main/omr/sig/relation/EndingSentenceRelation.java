//------------------------------------------------------------------------------------------------//
//                                                                                                //
//                           E n d i n g S e n t e n c e R e l a t i o n                          //
//                                                                                                //
//------------------------------------------------------------------------------------------------//
// <editor-fold defaultstate="collapsed" desc="hdr">
//  Copyright © Hervé Bitteur and others 2000-2016. All rights reserved.
//  This software is released under the GNU General Public License.
//  Goto http://kenai.com/projects/audiveris to report bugs or suggestions.
//------------------------------------------------------------------------------------------------//
// </editor-fold>
package omr.sig.relation;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Class {@code EndingSentenceRelation} represents a support relation between an ending
 * and its related text (such as "1." or "1,2").
 *
 * @author Hervé Bitteur
 */
@XmlRootElement(name = "ending-sentence")
public class EndingSentenceRelation
        extends AbstractSupport
{
}