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
import opennlp.tools.ngram.NGramModel;
import opennlp.tools.util.StringList;
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

  /**
   * Generates the context for person tokens
   * @param tokens person tokens to extract context from
   * @return the generated context
   */
  @Override
  public String[] getContext(String[] tokens) {
    Collection<String> context = new ArrayList<>();

    for (int i = 0; i < tokens.length; i++) {

      // last char
      context.add(String.format("gl%d=%s", i, tokens[i].charAt(tokens[0].length() - 1)));

      // ngrams
      NGramModel model = new NGramModel();
      model.add(normalizer.normalize(tokens[i]), 2, 3);

      // do i need a feature prefix?
      for (StringList tokenList : model) {
        if (tokenList.size() > 0) {
          context.add(tokenList.getToken(0));
        }
      }
    }

    return context.toArray(new String[context.size()]);
  }
}
