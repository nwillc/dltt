/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.dltt.model

import com.github.javafaker.Faker
import com.github.nwillc.dltt.model.LifeCycle.ACTIVE_CANCELLABLE
import com.github.nwillc.dltt.model.LifeCycle.AWAITING_PREMIUM_DEPOSIT
import com.github.nwillc.dltt.model.PolicyEvent.PREMIUM_RECEIVED
import com.github.nwillc.dltt.model.PolicyEvent.TERM_COMPLETE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class PolicyTest {
    private val faker = Faker()

    @Test
    internal fun initialLifeCycle() {
        val policyId = faker.idNumber().ssnValid()

        val policy = Policy(policyId)
        assertThat(policy.lifeCycle).isEqualTo(AWAITING_PREMIUM_DEPOSIT)
        assertThat(policy.isActive).isTrue()
    }

    @Test
    internal fun acceptValidEvent() {
        val policyId = faker.idNumber().ssnValid()

        val policy = Policy(policyId)
        assertThat(policy.accept(PREMIUM_RECEIVED)).isTrue()
    }

    @Test
    internal fun rejectInvalidEvent() {
        val policyId = faker.idNumber().ssnValid()

        val policy = Policy(policyId)
        assertThat(policy.accept(TERM_COMPLETE)).isFalse()
    }

    @Test
    internal fun eventAdvancesState() {
        val policyId = faker.idNumber().ssnValid()

        val policy = Policy(policyId)
        policy.accept(PREMIUM_RECEIVED)
        assertThat(policy.lifeCycle).isEqualTo(ACTIVE_CANCELLABLE)
    }
}