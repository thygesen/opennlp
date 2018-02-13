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

import java.util.ArrayList;
import java.util.Collection;

import opennlp.tools.ml.maxent.ContextGenerator;
import opennlp.tools.util.normalizer.AggregateCharSequenceNormalizer;
import opennlp.tools.util.normalizer.CharSequenceNormalizer;

/**
 * A context generator for gender detector.
 */
public class DefaultGenderDetectorContextGenerator implements ContextGenerator<String[]> {

  // lower case?, remove titles "president, dr., mr., ..."
  protected final CharSequenceNormalizer normalizer;

  public DefaultGenderDetectorContextGenerator() {
    this(new CharSequenceNormalizer[] {});
  }

  public DefaultGenderDetectorContextGenerator(CharSequenceNormalizer... normalizers) {
    this.normalizer = new AggregateCharSequenceNormalizer(normalizers);
  }

  private static int[] orderCountChar(String token) {
    int order_a = 0;
    int order_y = 0;
    int order_o = 0;
    int count_a = 0;
    for (int i = 0; i < token.length(); i++) {
      if (token.charAt(i) == 'a') {
        order_a += i + 1;
        count_a += 1;
      } else if (token.charAt(i) == 'y') {
        order_y += i + 1;
      } else if (token.charAt(i) == 'o') {
        order_o += i + 1;
      }

    }
    return new int[] {count_a, order_a, order_o, order_y};
  }

  /**
   * Generates the context for person tokens
   * @param tokens person tokens to extract context from
   * @return the generated context
   */
  @Override
  public String[] getContext(String[] tokens) {
    Collection<String> context = new ArrayList<>();

    // last char (first name)
    if (tokens.length > 0) {
      context.add(String.format("glst%d=%s", 0, tokens[0].charAt(tokens[0].length() - 1)));
      if (tokens[0].length() > 1)
        context.add(String.format("g2lst%d=%s", 0, tokens[0].charAt(tokens[0].length() - 2)));
      int[] counts = orderCountChar(tokens[0]);
      context.add(String.format("gca0=%d", counts[0]));
      context.add(String.format("goa0=%d", counts[1]));
      context.add(String.format("goo0=%d", counts[2]));
      context.add(String.format("goy0=%d", counts[3]));
    }
    // last char (middle name)
    if (tokens.length > 2) {
      context.add(String.format("glst1=%s", tokens[1].charAt(tokens[1].length() - 1)));
      if (tokens[1].length() > 1)
        context.add(String.format("g2lst1=%s", tokens[1].charAt(tokens[1].length() - 2)));
    }

    int idx = tokens.length - 1;
    if (tokens[idx].length() > 3)
      context.add(String.format("glst=%s", tokens[idx].substring(tokens[idx].length() - 3)));
    else
      context.add(String.format("glst=%s", tokens[idx]));

    return context.toArray(new String[context.size()]);
  }
}
