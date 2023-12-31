/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmail.com)
 */
package io.jenetics;

import java.util.random.RandomGenerator;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.jenetics.util.ContinuousRandom;
import io.jenetics.util.Factory;
import io.jenetics.util.RandomRegistry;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmail.com">Franz Wilhelmstötter</a>
 */
public class AnyGeneTest extends GeneTester<AnyGene<Integer>> {

	@Override
	protected Factory<AnyGene<Integer>> factory() {
		return () -> AnyGene.of(RandomRegistry.random()::nextInt);
	}

	@Test
	public void allowNullAlleles() {
		final Integer allele = null;
		final AnyGene<Integer> gene = AnyGene
			.of(allele, () -> null);

		Assert.assertNull(gene.allele());
		for (int i = 0; i < 10; ++i) {
			Assert.assertNull(gene.newInstance().allele());
		}
	}

	@Test
	public void newInstance() {
		final RandomGenerator random = new ContinuousRandom(0);
		final AnyGene<Integer> gene = AnyGene.of(random::nextInt);

		for (int i = 0; i < 100; ++i) {
			final AnyGene<Integer> g = gene.newInstance();
			Assert.assertEquals(g.allele().intValue(), i + 1);
		}
	}

	@Test
	public void isValid() {
		final AnyGene<Integer> gene = AnyGene.of(
			() -> RandomRegistry.random().nextInt(1000),
			i -> i < 100
		);

		for (int i = 0; i < 5000; ++i) {
			final AnyGene<Integer> g = gene.newInstance();

			Assert.assertEquals(g.isValid(), g.allele() < 100);
			Assert.assertTrue(g.allele() < 1000);
			Assert.assertTrue(g.allele() >= 0);
		}
	}

}
