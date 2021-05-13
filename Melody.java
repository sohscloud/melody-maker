package com.company.melody;

import java.util.*;

public class Melody {
    private Queue<Note> piece;
    //Initializes your melody to store the passed in Queue of Notes.
    public Melody(Queue<Note> song) {
        piece = song;
    }

    //Returns the total length of the song in seconds
    public double getTotalDuration() {
        double time = 0;
        boolean repeat = false;
        Iterator iterator = piece.iterator();
        double temp = 0;
        while (iterator.hasNext()) {
            Note n = (Note) iterator.next();
            if (n.isRepeat()) {
                if (repeat) {
                    //end of repeat
                    temp += n.getDuration();
                    time += temp*2;
                    temp = 0;
                    repeat = false;
                } else {
                    //start of new repeat
                    repeat = true;
                    temp += n.getDuration();
                }
                continue; // or break?
            }
            if (repeat) {
                temp += n.getDuration();
            } else time += n.getDuration();
        }
        return time;
    }

    //Returns a String containing information about each note. Each note should be on its own line and output using its
    // toString method.
    public String toString() {
        String information = "";
        Iterator iterator = piece.iterator();
        while (iterator.hasNext()) {
            information += iterator.next().toString() + "\n";
        }
        return information;
    }

    //Changes the tempo of each note to be tempo percent of what it formerly was. Passing a tempo of 1.0 will make the
    //tempo stay the same. tempo of 2.0 will make each note twice as long. tempo of 0.5 will make each note half as long.
    //Keep in mind that when the tempo changes the length of the song also changes.
    public void changeTempo(double tempo) {
        Iterator iterator = piece.iterator();
        while (iterator.hasNext()) {
            Note n = (Note) iterator.next();
            n.setDuration(tempo*n.getDuration());
        }
    }

    //Reverses the order of notes in the song, so that future calls to the play methods will play the notes in the opposite of the
    //order they were in before reverse was called. For example, a song containing notes A, F, G, then B would become
    //B, G, F, A. You may use one temporary Stack or one temporary Queue to help you solve this problem.
    public void reverse() {
        Stack<Note> s = new Stack<>();
        Iterator queue = piece.iterator();
        while (queue.hasNext()) {
            Note n = (Note) queue.next();
            s.push(n);
        }
        Iterator stack = s.iterator();
        while (stack.hasNext()) {
            piece.remove();
            piece.add(s.peek());
            s.pop();
        }
    }

    //Adds all notes from the given other song to the end of this song. For example, if this song is A,F,G,B and the other song
    //is F, C, D, your method should change this song to be A, F, G, B, F, C, D. The other song should be unchanged
    //after the call.
    public void append(Melody other) {
        Iterator i = other.piece.iterator();
        while (i.hasNext()) {
            Note n = (Note) i.next();
            piece.add(n);
        }
    }

    //Plays the song by calling each note's play method.
    public void play() {
        Iterator i = piece.iterator();
        boolean repeat = false;
        Queue<Note> excerpt = new LinkedList<>();
        while (i.hasNext()) {
            Note n = (Note) i.next();
            if (n.isRepeat()) {
                excerpt.add(n);
                n.play();
                if (repeat) {
                    Iterator x = excerpt.iterator();
                    while (x.hasNext()) {
                        Note m = excerpt.peek();
                        m.play();
                        excerpt.remove();
                    }
                    repeat = false;
                    excerpt.clear();
                } else {
                    repeat = true;
                }
                continue;
            }
            if (repeat) excerpt.add(n);
            n.play();
        }
    }
}
