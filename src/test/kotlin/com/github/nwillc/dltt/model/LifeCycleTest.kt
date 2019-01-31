/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.dltt.model

import com.github.nwillc.dltt.model.LifeCycle.ACTIVE
import com.github.nwillc.dltt.model.LifeCycle.ACTIVE_CANCELLABLE
import com.github.nwillc.dltt.model.LifeCycle.AWAITING_PREMIUM_DEPOSIT
import com.github.nwillc.dltt.model.LifeCycle.CLOSED_CANCELLED
import com.github.nwillc.dltt.model.LifeCycle.CLOSED_OWNER_DIED
import com.github.nwillc.dltt.model.LifeCycle.CLOSED_TERM_COMPLETE
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class LifeCycleTest {
    @Test
    fun getStateActive() {
        assertThat(AWAITING_PREMIUM_DEPOSIT.state).isEqualTo(State.ACTIVE)
        assertThat(ACTIVE_CANCELLABLE.state).isEqualTo(State.ACTIVE)
        assertThat(ACTIVE.state).isEqualTo(State.ACTIVE)
    }

    @Test
    fun getStateClosed() {
        assertThat(CLOSED_CANCELLED.state).isEqualTo(State.CLOSED)
        assertThat(CLOSED_OWNER_DIED.state).isEqualTo(State.CLOSED)
        assertThat(CLOSED_TERM_COMPLETE.state).isEqualTo(State.CLOSED)
    }

    @Test
    internal fun validTransitions() {
        LifeCycle.values()
            .asSequence()
            .forEach { lifeCycle ->
                lifeCycle.allowedEvents.forEach { event ->
                    assertThat(lifeCycle.allowableEvent(event)).isTrue()
                }
            }
    }

    @Test
    internal fun invalidTransitions() {
        LifeCycle.values()
            .asSequence()
            .forEach { lifeCycle ->
                PolicyEvents.values()
                    .asSequence()
                    .filter { !lifeCycle.allowedEvents.contains(it) }
                    .forEach {
                        assertThat(lifeCycle.allowableEvent(it)).isFalse()
                    }
            }
    }
}