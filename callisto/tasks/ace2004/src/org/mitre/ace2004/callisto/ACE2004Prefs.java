/*
 * Copyright (c) 2002-2006 The MITRE Corporation
 * 
 * Except as permitted below
 * ALL RIGHTS RESERVED
 * 
 * The MITRE Corporation (MITRE) provides this software to you without
 * charge to use for your internal purposes only. Any copy you make for
 * such purposes is authorized provided you reproduce MITRE's copyright
 * designation and this License in any such copy. You may not give or
 * sell this software to any other party without the prior written
 * permission of the MITRE Corporation.
 * 
 * The government of the United States of America may make unrestricted
 * use of this software.
 * 
 * This software is the copyright work of MITRE. No ownership or other
 * proprietary interest in this software is granted you other than what
 * is granted in this license.
 * 
 * Any modification or enhancement of this software must inherit this
 * license, including its warranty disclaimers. You hereby agree to
 * provide to MITRE, at no charge, a copy of any such modification or
 * enhancement without limitation.
 * 
 * MITRE IS PROVIDING THE PRODUCT "AS IS" AND MAKES NO WARRANTY, EXPRESS
 * OR IMPLIED, AS TO THE ACCURACY, CAPABILITY, EFFICIENCY,
 * MERCHANTABILITY, OR FUNCTIONING OF THIS SOFTWARE AND DOCUMENTATION. IN
 * NO EVENT WILL MITRE BE LIABLE FOR ANY GENERAL, CONSEQUENTIAL,
 * INDIRECT, INCIDENTAL, EXEMPLARY OR SPECIAL DAMAGES, EVEN IF MITRE HAS
 * BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 * 
 * You accept this software on the condition that you indemnify and hold
 * harmless MITRE, its Board of Trustees, officers, agents, and
 * employees, from any and all liability or damages to third parties,
 * including attorneys' fees, court costs, and other related costs and
 * expenses, arising out of your use of this software irrespective of the
 * cause of said liability.
 * 
 * The export from the United States or the subsequent reexport of this
 * software is subject to compliance with United States export control
 * and munitions control restrictions. You agree that in the event you
 * seek to export this software you assume full responsibility for
 * obtaining all necessary export licenses and approvals and for assuring
 * compliance with applicable reexport restrictions.
 */

package org.mitre.ace2004.callisto;

import javax.swing.JCheckBox;

import org.mitre.jawb.Jawb;
import org.mitre.jawb.tasks.Task;
import org.mitre.jawb.prefs.AbstractPreferencePane;
import org.mitre.jawb.prefs.Preferences;


/**
 * This is the place for any necessary hard-coded information about the task
 * to reside.
 */
class ACE2004Prefs extends AbstractPreferencePane {

  private JCheckBox entityArgs;
  
  public ACE2004Prefs(Task task) {
    super(task.getTitle());
    addComponent (entityArgs = new JCheckBox
      ("Allow Relation and Event Args to be Entities"));
    
    reset ();
  }
  
  public void save () {
    Preferences prefs = Jawb.getPreferences();
    prefs.setPreference (ACE2004Task.ENTITY_ARGS_PREFERENCE_KEY,
                         entityArgs.isSelected());
  }

  public void reset () {
    Preferences prefs = Jawb.getPreferences();
    entityArgs.setSelected (prefs.getBoolean
                           (ACE2004Task.ENTITY_ARGS_PREFERENCE_KEY));
  }
}
