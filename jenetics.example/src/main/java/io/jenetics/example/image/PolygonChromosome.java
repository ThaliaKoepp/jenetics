/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.jenetics.example.image;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serial;
import java.io.Serializable;

import io.jenetics.AbstractChromosome;
import io.jenetics.Chromosome;
import io.jenetics.util.ISeq;

/**
 * A simple chromosome representing a list of polygons.
 */
final class PolygonChromosome
	extends AbstractChromosome<PolygonGene>
	implements Serializable
{
	@Serial
	private static final long serialVersionUID = 2L;

	PolygonChromosome(final ISeq<PolygonGene> genes) {
		super(genes);
	}

	PolygonChromosome(final int polygonCount, final int polygonLength) {
		super(PolygonGene.seq(polygonCount, polygonLength));
	}

	@Override
	public Chromosome<PolygonGene> newInstance(final ISeq<PolygonGene> genes) {
		return new PolygonChromosome(genes);
	}

	@Override
	public Chromosome<PolygonGene> newInstance() {
		return new PolygonChromosome(length(), gene().allele().length());
	}

	public void draw(final Graphics2D g, final int width, final int height) {
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, width, height);

		g.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));

		for (PolygonGene gene : this) {
			gene.allele().draw(g, width, height);
		}
	}

}
