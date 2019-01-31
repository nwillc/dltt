/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.dltt.model

enum class LifeCycle(val state: State, val allowedEvents: List<PolicyEvent>) {
    AWAITING_PREMIUM_DEPOSIT(State.ACTIVE,
        listOf(PolicyEvent.PREMIUM_RECEIVED)),
    ACTIVE_CANCELLABLE(State.ACTIVE,
        listOf(PolicyEvent.CANCELLED_BY_OWNER, PolicyEvent.ACTIVE_ONE_MONTH)),
    CLOSED_CANCELLED(State.CLOSED, emptyList()),
    ACTIVE(State.ACTIVE,
        listOf(PolicyEvent.DEATH_OF_OWNER, PolicyEvent.ONE_MONTH, PolicyEvent.TERM_COMPLETE)),
    CLOSED_OWNER_DIED(State.CLOSED, emptyList()),
    CLOSED_TERM_COMPLETE(State.CLOSED, emptyList());

    fun allowableEvent(event: PolicyEvent): Boolean = allowedEvents.contains(event)
}