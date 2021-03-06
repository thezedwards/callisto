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

package org.mitre.jawb.tasks.preannotate;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Set;

import gov.nist.atlas.type.AnnotationType;

import org.mitre.jawb.atlas.*;
import org.mitre.jawb.tasks.*;
import org.mitre.jawb.io.*;
import org.mitre.jawb.tasks.*;

import org.mitre.jawb.Jawb;
import org.mitre.jawb.gui.JawbDocument;

public class PreAnnotateLocalImporter implements Importer {
  private LP lp;
  private String mt, encoding;

  public PreAnnotateLocalImporter() {
    this.lp = null;
  }

  public void setLP(LP lp) { this.lp = lp; }
  public void setMIMEType(String mt) { this.mt = mt; }
  public void setEncoding(String encoding) { this.encoding = encoding; }
  
  public String getFormat() { return "fake-preannotate-format"; } /* wtf is this for? */
  public String toString() { return getFormat(); }
  public String getDescription() { return "pseudo-importer for pre-annotation"; } /* ditto */

  public AWBDocument importDocument(URI uri, String encoding) throws IOException {
    System.out.println(">>> importing " + uri + ": lp = " + lp + ", task = " + lp.getTask() + ", mimetype = " + mt);

    BufferedReader reader = null;
    URL url = null;

    try {
      url = uri.toURL();
      reader = new BufferedReader(new InputStreamReader(url.openStream()));
    }
    catch(IllegalArgumentException e) {
      throw new IOException("can't convert import uri to url: " + e.getMessage());
    }
    catch(java.net.MalformedURLException e) {
      throw new IOException("can't convert import uri to url: " + e.getMessage());
    }

    Task task = Jawb.getTaskManager().getTaskByName(lp.getTask());
    if(task == null) throw new IOException("unknown task " + task);

    DefaultInlineImporter inlineImp = new DefaultInlineImporter(task, "wtf is this for");

    String s, contents = "";
    while((s = reader.readLine()) != null) { contents += s + "\n"; }

    AWBDocument x = null;
    try {
      String ret = lp.tag(contents, mt);
      File tempFile = File.createTempFile("callisto","sgml");
      x = inlineImp.importDocument(new StringReader(ret), tempFile.toString(),"UTF-8");
      System.out.println("returning post-phragged document " + x);
    }
    catch(LPException e) { throw new IOException("trouble running LP: " + e.getMessage()); }
    
    return x;
  }
}

