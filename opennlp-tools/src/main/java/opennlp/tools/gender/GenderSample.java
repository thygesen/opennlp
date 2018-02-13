package opennlp.tools.gender;

import java.io.Serializable;
import java.util.Objects;

public class GenderSample implements Serializable {

  private final String gender;
  private final String[] context;

  public GenderSample(String gender, String[] context) {
    this.gender = Objects.requireNonNull(gender, "gender must not be null");
    this.context = Objects.requireNonNull(context, "context must not be null");
  }

  public String getGender() {
    return gender;
  }

  public String[] getContext() {
    return context;
  }

  @Override
  public String toString() {
    return gender + '\t' +  context;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getContext(), getGender());
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj instanceof GenderSample) {
      GenderSample a = (GenderSample) obj;

      return getGender().equals(a.getGender())
              && getContext().equals(a.getContext());
    }

    return false;
  }
}
