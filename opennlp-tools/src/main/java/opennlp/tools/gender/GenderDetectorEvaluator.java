/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.tools.gender;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import opennlp.tools.util.eval.Evaluator;
import opennlp.tools.util.eval.Mean;

public class GenderDetectorEvaluator extends Evaluator<GenderSample> {

  private Mean accuracy = new Mean();
  private Map<String,Integer> right = new HashMap<>();
  private Map<String,Integer> wrong = new HashMap<>();

  /**
   * The {@link GenderDetector} used to create the predicted
   * {@link GenderSample} objects.
   */
  private GenderDetector genderDetector;

  /**
   * Initializes the current instance with the given
   * {@link GenderDetector}.
   *
   * @param genderDetector the {@link GenderDetector} to evaluate.
   * @param listeners evaluation sample listeners
   */
  public GenderDetectorEvaluator(GenderDetector genderDetector,
                                  GenderDetectorEvaluationMonitor ... listeners) {
    super(listeners);
    this.genderDetector = genderDetector;
  }

  /**
   * Evaluates the given reference {@link GenderSample} object.
   *
   * This is done by finding the names in the sentence and using
   * {@link GenderDetector} to detect the gender for the names
   * {@link GenderSample}. The found gender used to
   * calculate and update the scores.
   *
   * @param reference the reference {@link GenderSample}.
   *
   * @return the predicted {@link GenderSample}.
   */
  @Override
  protected GenderSample processSample(GenderSample reference) {

    String predicted = genderDetector.genderDetect(reference.getContext());

    if (reference.getGender().equals(predicted)) {
      accuracy.add(1);
      if (right.containsKey(predicted)) {
        right.put(predicted, right.get(predicted) + 1);
      } else {
        right.put(predicted, 1);
      }
    }
    else {
      accuracy.add(0);
      if (wrong.containsKey(predicted)) {
        wrong.put(predicted, wrong.get(predicted) + 1);
      } else {
        wrong.put(predicted, 1);
      }
    }

    return new GenderSample(predicted, reference.getContext());
  }

  public double getAccuracy() {
    return accuracy.mean();
  }



  /**
   * Represents this objects as human readable {@link String}.
   */
  @Override
  public String toString() {
    // move to report
    String correct = "Correct:\n" + right.entrySet().stream()
            .map(e -> String.format("\t%s: %d", e.getKey(), e.getValue()))
            .collect(Collectors.joining("\n"));
    String misclassified = "Misclassified:\n" + wrong.entrySet().stream()
            .map(e -> String.format("\t%s: %d", e.getKey(), e.getValue()))
            .collect(Collectors.joining("\n"));
    return "Accuracy: " + accuracy.mean() + "\n" +
            "Number of documents: " + accuracy.count() + "\n" +
            correct + "\n" + misclassified + "\n";
  }
}
