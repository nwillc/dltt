/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.dltt.model

import com.github.nwillc.dltt.model.PolicyEvent.*

class Policy(val id: String, val durationMonths: Int = 12) {
    var lifeCycle = LifeCycle.AWAITING_PREMIUM_DEPOSIT
        private set
    val isActive: Boolean
        get() = lifeCycle.state == State.ACTIVE
    var currentMonth: Int = 0
        private set

    fun accept(event: PolicyEvent): Boolean {
        // Check if this event is *basically* at the right time
        if (!lifeCycle.allowableEvent(event)) {
            return false
        }
        when(event) {
            CANCELLED_BY_OWNER -> fullPayout()
            TERM_COMPLETE -> {
                if (currentMonth != durationMonths) {
                    return false
                }
                fullPayout()
            }
            DEATH_OF_OWNER -> {
                changePayee()
                fullPayout()
            }
            ACTIVE_ONE_MONTH, ONE_MONTH -> {
                incrementMonth()
                monthylyPayout()
            }
            PREMIUM_RECEIVED -> beginClock()
        }
        lifeCycle = event.nextLifeCycle
        return true
    }

    private fun beginClock() {
        currentMonth = 0
    }

    private fun incrementMonth() {
        currentMonth++
    }

    private fun monthylyPayout() {
        println("monthly payout")
    }

    private fun changePayee() {
        println("change payee")
    }

    private fun fullPayout() {
        println("make payout")
    }

    override fun toString(): String {
        return "Policy(id='$id', durationMonths=$durationMonths, lifeCycle=$lifeCycle, currentMonth=$currentMonth)"
    }

}