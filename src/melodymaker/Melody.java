package melodymaker;
// TODO: Write the code for this class.

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Melody {

    Deque<Note> queue;
    Deque<Note> temporaryQueue;
    Deque<Note> stack;

    public Melody(Deque<Note> song) {

        queue = new LinkedList<>();

        Note note;

        while(!song.isEmpty()){
            note = song.remove();
            queue.add(note);
        }
    }

    public double getTotalDuration() {

        Note note;
        double totalDuration = 0.0;

        for (int i = 0; i < queue.size(); i++) {
            note = queue.remove();
            queue.add(note);
            totalDuration += note.getDuration();
        }
        return totalDuration;
    }

    public String toString() {

        Note note;
        String printNote = "";

        for (int i = 0; i < queue.size(); i++) {
            note = queue.remove();
            queue.add(note);
            printNote += note.toString();
        }
        return printNote;
    }

    public void changeTempo(double tempo) {

        Note note;
        double currentTempo = 0.0;

        for (int i = 0; i < queue.size(); i++) {
            note = queue.remove();
            queue.add(note);
            currentTempo = note.getDuration();
            note.setDuration(tempo * currentTempo);
        }
        getTotalDuration();
    }

    //Reverse a queue using a stack
    public void reverse() {
        
        stack = new LinkedList<>();
        
        while (!queue.isEmpty()) {
            stack.push(queue.peek());
            queue.remove();
        }
        while (!stack.isEmpty()) {
            queue.add(stack.peek());
            stack.pop();
        }
    }

    public void append(Melody other) {

        Note note;

        for (int i = 0; i < other.queue.size(); i++) {
            note = other.queue.remove();
            queue.add(note);
        }
    }

    public void play() {

        Note note;

        for (int i = 0; i < queue.size(); i++) {
            
            note = queue.remove();
            queue.add(note);

            if (note.isRepeat()) {
                
                temporaryQueue = new LinkedList<>();
                
                //First note added and removed
                temporaryQueue.add(note);
                note.play();
                queue.remove(note);
                
                //Get the second note
                note = queue.remove();
                queue.add(note);

                while (!note.isRepeat()) {
                    
                    //Add notes to temporary queue
                    temporaryQueue.add(note);
                    note.play();
                    
                    //Get the next note
                    queue.add(note);
                    note = queue.remove();
                } 
                
                temporaryQueue.add(note);
                queue.add(note);
                note.play();

                //Loop through temporary queue and play each note
                while(!temporaryQueue.isEmpty()){
                    note = temporaryQueue.remove();
                    note.play();
                }
                
//                for (Note notes: temporaryQueue) {
//                    notes.play();
//                }
            } 
            else {
                note.play();
                //queue.remove(note);
                //i++;
                
            }
        }
    }
}