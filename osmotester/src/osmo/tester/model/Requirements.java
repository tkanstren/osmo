package osmo.tester.model;

import osmo.tester.generator.testsuite.TestSuite;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Used to define a set of requirements that should be covered and to evaluate which ones have been covered overall.
 * Additionally, each {@link osmo.tester.generator.testsuite.TestStep} also defines which requirements have been
 * covered in which test case and test step. A requirement is defined as a string that is expected to be both its
 * unique identifier and expressive enough to describe the actual intent of the requirement for the user to be able
 * to understand what it is.
 *
 * @author Teemu Kanstren
 */
public class Requirements {
  /** The overall set of requirements that should be covered. */
  private Collection<String> requirements = new ArrayList<String>();
  /** The set of requirements that have been covered. */
  private Collection<String> covered = new HashSet<String>();
  /** The set of generated tests cases, including the one currently under generation. */
  private TestSuite testSuite = null;

  public void setTestSuite(TestSuite testSuite) {
    this.testSuite = testSuite;
  }

  /**
   * Adds a new requirement that should be covered.
   *
   * @param requirement The identifier of the requirement.
   */
  public void add(String requirement) {
    //check if already exists
    if (requirements.contains(requirement)) {
      throw new IllegalArgumentException("Attempted to register '"+requirement+"' twice. Duplicates not allowed.");
    }
    requirements.add(requirement);
  }

  /**
   * Marks the given requirement as covered.
   *
   * @param requirement The identifier of the requirement.
   */
  public void covered(String requirement) {
    covered.add(requirement);
    testSuite.covered(requirement);
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

  /**
   * Creates a string representation of the set of covered requirements vs the set of all defined requirements.
   *
   * @return Message that can be printed to tell the requirements coverage.
   */
  //TODO: add check for requirements that is not listed in expected requirements but is marked as covered
  public String printCoverage() {
    if (requirements.size() == 0) {
      return "No requirements defined. Not calculating or showing requirements coverage. \n" +
              "If you want to see requirements coverage you need to define a @RequirementsField\n" +
              "and add some requirements and their coverage into the model.";
    }
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
