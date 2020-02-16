package dev.pkos98.clanki.exception

public class DecknameDuplicateException(duplicateName: String) :
    Exception("Deckname '$duplicateName' is already being used. Please use another deckName.")
