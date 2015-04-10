/*
 *  Created by Filip P. on 3/14/15 9:21 AM.
 */

package me.pauzen.alphacore.utils.date.time;

import javax.annotation.Nonnull;

class TimeImpl implements Time, Comparable<Time> {

    long hours, minutes, seconds;

    protected TimeImpl(long hours, long minutes, long seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    @Override
    public long toMilliseconds() {
        return (this.getHour() * 60) * 60 * 1000 + this.getMinute() * 60 * 1000 + this.getSecond() * 1000;
    }

    @Override
    public Time compareAccurately(Time time) {
        return new TimeImpl(hours - time.getHour(), minutes - time.getMinute(), seconds - time.getSecond());
    }

    @Override
    public Time compareInaccurately(Time time) {
        return new TimeImpl(hours - time.getHour(), minutes - time.getMinute(), 0);
    }

    @Override
    public long getHour() {
        return hours;
    }

    @Override
    public long getMinute() {
        return minutes;
    }

    @Override
    public long getSecond() {
        return seconds;
    }

    @Override
    public String toString() {
        return "TimeImpl{" +
                "hours=" + hours +
                ", minutes=" + minutes +
                ", seconds=" + seconds +
                '}';
    }

    @Override
    public String asTimeStamp() {
        return hours + ":" + minutes + ":" + seconds;
    }

    @Override
    public boolean equals(Time time1) {
        if (this == time1) return true;
        if (time1 == null || getClass() != time1.getClass()) return false;

        TimeImpl time = (TimeImpl) time1;

        return hours == time.hours && minutes == time.minutes && seconds == time.seconds;

    }

    @Override
    public int hashCode() {
        int result = (int) (hours ^ (hours >>> 32));
        result = 31 * result + (int) (minutes ^ (minutes >>> 32));
        result = 31 * result + (int) (seconds ^ (seconds >>> 32));
        return result;
    }

    @Override
    public int compareTo(@Nonnull Time compared) {
        return (int) (toMilliseconds() - compared.toMilliseconds());
    }
}
