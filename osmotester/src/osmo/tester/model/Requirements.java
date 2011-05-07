package osmo.tester.model;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Teemu Kanstren
 */
public class Requirements {
  private Collection<String> requirements = new ArrayList<String>();
  private Collection<String> covered = new HashSet<String>();

  public void add(String requirement) {
    //check if already exists
    if (requirements.contains(requirement)) {
      throw new IllegalArgumentException("Attempted to register '"+requirement+"' twice. Duplicates not allowed.");
    }
    requirements.add(requirement);
  }

  public void covered(String requirement) {
    covered.add(requirement);
  }

  public Collection<String> getRequirements() {
    return requirements;
  }

  public Collection<String> getCovered() {
    return covered;
  }

  public Collection<String> getExcess() {
    return null;
  }

  public String printCoverage() {
    StringBuilder out = new StringBuilder();
    out.append("Requirements:"+requirements+"\n");
    out.append("Covered:"+covered+"\n");
    int n = covered.size();
    int total = requirements.size();
    double p = n/total*100;
    final MessageFormat format = new MessageFormat("Total = {0}/{1} ({2}%) requirements.");
    Object args = new Object[] {n, total, p};
    out.append(format.format(args));
    //TODO: add tests for coverage. including excess coverage
    return out.toString();
  }
}
