package opennlp.tools.gender;

import opennlp.tools.ml.maxent.ContextGenerator;
import opennlp.tools.ml.model.Event;
import opennlp.tools.util.AbstractEventStream;
import opennlp.tools.util.ObjectStream;

import java.util.Iterator;

public class GenderDetectorEventStream extends AbstractEventStream<GenderSample> {

  private ContextGenerator contextGenerator;

  public GenderDetectorEventStream(ObjectStream<GenderSample> samples) {
    this(samples, new DefaultGenderDetectorContextGenerator());
  }

  public GenderDetectorEventStream(ObjectStream<GenderSample> samples, ContextGenerator<String[]> contextGenerator) {
    super(samples);
    this.contextGenerator = contextGenerator;
  }

  @Override
  protected Iterator<Event> createEvents(GenderSample sample) {
    return new Iterator<Event>() {

      private boolean isVirgin = true;

      public boolean hasNext() {
        return isVirgin;
      }

      public Event next() {

        isVirgin = false;

        return new Event(sample.getGender(),
                contextGenerator.getContext(sample.getContext()));
      }
    };
  }
}
