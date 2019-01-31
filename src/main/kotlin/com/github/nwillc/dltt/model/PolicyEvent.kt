/*
 * Copyright 2019 nwillc@gmail.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted, provided that the above copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.github.nwillc.dltt.model

/**
 * Why am I using a String for _nextLifeCycle and a LifeCycle.valueOf() getter here? Ask Kotlin ...
 * When I used the nextLifeCycle: LifeCycle, everything compiled clean but the
 * values were all *null* ?!  Just hacking this now to move on.
 */
enum class PolicyEvent(private val _nextLifeCycle: String) {
    PREMIUM_RECEIVED("ACTIVE_CANCELLABLE"),
    CANCELLED_BY_OWNER("CLOSED_CANCELLED"),
    ACTIVE_ONE_MONTH("ACTIVE"),
    DEATH_OF_OWNER("CLOSED_OWNER_DIED"),
    ONE_MONTH("ACTIVE"),
    TERM_COMPLETE("CLOSED_TERM_COMPLETE");

    val nextLifeCycle: LifeCycle
        get() = LifeCycle.valueOf(_nextLifeCycle)
}