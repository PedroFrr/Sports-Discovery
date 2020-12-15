package com.pedrofr.sportsfinder.viewmodels

import com.pedrofr.sportsfinder.data.model.Bet
import com.pedrofr.sportsfinder.data.model.BetWithEvents
import com.pedrofr.sportsfinder.data.model.Event
import org.junit.Assert.*
import org.junit.Test

class EventHistoryViewModelTest{

    @Test
    fun `validate if can settle bet`(){
        val event = Event("123123", "123123", 150000, "teamA", "teamB", 1.85, 2.5, 3.0)
        val bet = Bet("12", "dsdds", 100.0, "sdds", 1.8,
            isSettled = true,
            isWon = true,
            isPending = false
        )
        val betWithEvents = listOf<BetWithEvents> (
          BetWithEvents(bet, listOf(event))
        )




    }

}