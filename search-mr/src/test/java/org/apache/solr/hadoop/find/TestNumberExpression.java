/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.solr.hadoop.fs.shell.find;

import static org.junit.Assert.*;

import java.io.IOException;

import org.apache.solr.hadoop.fs.shell.find.FindOptions;
import org.apache.solr.hadoop.fs.shell.find.NumberExpression;
import org.apache.solr.hadoop.fs.shell.find.Result;
import org.junit.Test;

public class TestNumberExpression extends TestExpression {

  @Test
  public void applyNumberEquals() throws IOException {
    NumberExpression numberExpr = new NumberExpression(){};
    addArgument(numberExpr, "5");
    numberExpr.initialise(new FindOptions());
    assertEquals(Result.PASS, numberExpr.applyNumber(5));
    assertEquals(Result.FAIL, numberExpr.applyNumber(4));
    assertEquals(Result.FAIL, numberExpr.applyNumber(6));
  }

  @Test
  public void applyNumberGreaterThan() throws IOException {
    NumberExpression numberExpr = new NumberExpression(){};
    addArgument(numberExpr, "+5");
    numberExpr.initialise(new FindOptions());
    assertEquals(Result.FAIL, numberExpr.applyNumber(5));
    assertEquals(Result.FAIL, numberExpr.applyNumber(4));
    assertEquals(Result.PASS, numberExpr.applyNumber(6));
  }

  @Test
  public void applyNumberLessThan() throws IOException {
    NumberExpression numberExpr = new NumberExpression(){};
    addArgument(numberExpr, "-5");
    numberExpr.initialise(new FindOptions());
    assertEquals(Result.FAIL, numberExpr.applyNumber(5));
    assertEquals(Result.PASS, numberExpr.applyNumber(4));
    assertEquals(Result.FAIL, numberExpr.applyNumber(6));
  }
}
