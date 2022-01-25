package util;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class AnimationHelper {

    public static void showAnim(DoubleProperty start,double end){
            Timeline timeline = new Timeline();
            KeyValue kv = new KeyValue(start,end, Interpolator.EASE_IN);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(.2),kv);
            timeline.getKeyFrames().add(keyFrame);
            timeline.play();
    }

    public static void hideAnim(DoubleProperty start,double end,AnimationListener animationListener){
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(start,end, Interpolator.EASE_IN);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(.2),kv);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
        timeline.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (animationListener!=null)
                    animationListener.onAnimationFinished();
            }
        });
    }

    public interface AnimationListener{
        void onAnimationFinished();
    }
}
