package com.codemanship.marsrover;


import org.junit.Before;
import org.junit.Test;
import refactoring.ViewPoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CameraViewPoint_ {
    private Camera camera;
    private ImageProcessor imageProcessor;
    private CameraViewPoint initialViewPoint;
    private CameraView cameraView;

    @Before
    public void setup(){
        camera = mock(Camera.class); // Collaborators (MOCKS) : Camera & ImageProcessor
        imageProcessor = mock(ImageProcessor.class);
        cameraView = mock(CameraView.class);
        initialViewPoint = new CameraViewPoint(camera, imageProcessor); // SUT: CameraViewPoint
    }

    @Test
    public void name() {
        ViewPoint viewPoint = initialViewPoint.turnLeft().turnLeft();
        assertThat(viewPoint).isNotNull().isNotEqualTo(initialViewPoint);

    }

    @Test
    public void given_no_obstacle_should_provide_a_new_viewpoint() {
        ViewPoint viewPoint = initialViewPoint.forward();
        verify(camera).getFrontView();
        verify(imageProcessor).getObstacle();
        when(imageProcessor.getObstacle(any())).thenReturn(-1);

        assertThat(viewPoint).isNotNull().isNotEqualTo(initialViewPoint);
    }

    @Test
    public void testCameraViewPoint_Forward() {
        initialViewPoint.forward();
        verify(camera).getFrontView();
        verify(imageProcessor).getObstacle();
    }

    @Test
    public void testCameraViewPoint_Backward() {
        initialViewPoint.backward();
        verify(camera).getRearView();
        verify(imageProcessor).getObstacle();
    }

    @Test
    public void testCameraViewPoint_turnLeft() {
        initialViewPoint.turnLeft();
        verify(camera).getFrontView();
        verify(camera).turn(50);
    }

    @Test
    public void testCameraViewPoint_turnRight() {
        initialViewPoint.turnRight();
        verify(camera).getFrontView();
        verify(camera).turn(-50);
    }

    private class CameraViewPoint implements ViewPoint{
        private final Camera camera;
        private final ImageProcessor imageProcessor;

        public CameraViewPoint(Camera camera, ImageProcessor imageProcessor) {
            this.camera = camera;
            this.imageProcessor = imageProcessor;
        }

        public Camera getCamera() {
            return camera;
        }

        public ImageProcessor getImageProcessor() {
            return imageProcessor;
        }

        @Override
        public ViewPoint turnLeft() {
            camera.getFrontView();
            camera.turn(50);
            return new CameraViewPoint(camera,imageProcessor);
        }

        @Override
        public ViewPoint turnRight() {
            camera.getFrontView();
            camera.turn(-50);
            return new CameraViewPoint(camera,imageProcessor);
        }

        @Override
        public ViewPoint forward() {
            camera.getFrontView();
            imageProcessor.getObstacle();
            return new CameraViewPoint(camera,imageProcessor);
        }

        @Override
        public ViewPoint backward() {
            camera.getRearView();
            imageProcessor.getObstacle();
            return new CameraViewPoint(camera,imageProcessor);
        }
    }

    private class Camera{
        private int degrees = 0;
        private CameraView cameraView;

        public Camera(CameraView cameraView) {
            this.cameraView = cameraView;
        }

        public int getDegrees() {
            return degrees;
        }

        public byte[] getFrontView(){
            return cameraView.getImage();
        }

        public byte[] getRearView(){
            return cameraView.getImage();
        }

        public void turn(int degrees){
            cameraView.getImage();
        }
    }

    private interface ImageProcessor{
        int getObstacle(byte[] image);

        void getObstacle();
    }

    private class CameraView{
        private byte[] image;

        public CameraView(byte[] image) {
            this.image = image;
        }

        public byte[] getImage(){
            return image;
        }
    }

}
