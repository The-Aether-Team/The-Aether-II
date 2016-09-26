package us.ichun.mods.ichunutil.common.module.tabula.common.project;

/**
 * A utility class used to convert angles between their representations in Minecraft and Techne.
 *
 * Not Gimbal lock safe (http://en.wikipedia.org/wiki/Gimbal_lock)
 *
 * @version 1.0
 * @author Vswe
 */
public class TechneConverter {

    /**
     * Convert Techne angles (on the form YZX) to Minecraft angles (on the form ZYX).
     * @param rotation Rotation object containing the Techne angles (YZX)
     * @return Rotation object containing the Minecraft angles (ZYX)
     */
    public static Rotation fromTechne(Rotation rotation) {
        Matrix3x3 rotationMatrixX = Matrix3x3.createRotationMatrixX(rotation.getRadiansX());
        Matrix3x3 rotationMatrixY = Matrix3x3.createRotationMatrixY(rotation.getRadiansY());
        Matrix3x3 rotationMatrixZ = Matrix3x3.createRotationMatrixZ(rotation.getRadiansZ());

        Matrix3x3 combinedRotation = rotationMatrixY.multiply(rotationMatrixZ).multiply(rotationMatrixX);


        return Rotation.createFromMatrixZYX(combinedRotation);
    }

    /**
     * Convert Minecraft angles (on the form ZYX) to Techne angles (on the form YZX).
     * @param rotation Rotation object containing the Minecraft angles (ZYX)
     * @return Rotation object containing the Techne angles (YZX)
     */
    public static Rotation toTechne(Rotation rotation) {
        Matrix3x3 rotationMatrixX = Matrix3x3.createRotationMatrixX(rotation.getRadiansX());
        Matrix3x3 rotationMatrixY = Matrix3x3.createRotationMatrixY(rotation.getRadiansY());
        Matrix3x3 rotationMatrixZ = Matrix3x3.createRotationMatrixZ(rotation.getRadiansZ());

        Matrix3x3 combinedRotation = rotationMatrixZ.multiply(rotationMatrixY).multiply(rotationMatrixX);


        return Rotation.createFromMatrixYZX(combinedRotation);
    }

    /**
     * A very basic matrix class. Only supports 3x3 matrices.
     */
    public static class Matrix3x3 {
        private double[] data;

        private Matrix3x3(double... data) {
            this.data = data;
        }


        /**
         * Creates the rotation matrix around the X-axis with the given rotation.
         * @param angle The angle of which the matrix is rotated around the X-axis.
         * @return A new X rotation matrix
         */
        public static Matrix3x3 createRotationMatrixX(double angle) {
            double c = Math.cos(angle);
            double s = Math.sin(angle);
            return new Matrix3x3(
                    1,  0,  0,
                    0,  c, -s,
                    0,  s,  c
            );
        }

        /**
         * Creates the rotation matrix around the Y-axis with the given rotation.
         * @param angle The angle of which the matrix is rotated around the Y-axis.
         * @return A new Y rotation matrix
         */
        public static Matrix3x3 createRotationMatrixY(double angle) {
            double c = Math.cos(angle);
            double s = Math.sin(angle);
            return new Matrix3x3(
                    c,  0,  s,
                    0,  1, 0,
                    -s,  0,  c
            );
        }

        /**
         * Creates the rotation matrix around the Z-axis with the given rotation.
         * @param angle The angle of which the matrix is rotated around the Z-axis.
         * @return A new Z rotation matrix
         */
        public static Matrix3x3 createRotationMatrixZ(double angle) {
            double c = Math.cos(angle);
            double s = Math.sin(angle);
            return new Matrix3x3(
                    c,  -s,  0,
                    s,  c, 0,
                    0,  0,  1
            );
        }

        /**
         * Multiply one matrix with another. This matrix is multiplied with the parameter one, not the other way around.
         * @param other The matrix to multiply with.
         * @return A new matrix instance. The multiplied matrices are not changed.
         */
        public Matrix3x3 multiply(Matrix3x3 other) {
            Matrix3x3 result = new Matrix3x3(new double[9]);

            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    double val = 0;
                    for (int i = 0; i < 3; i++) {
                        val += this.getValue(i, y) * other.getValue(x, i);
                    }
                    result.setValue(x, y, val);
                }
            }

            return result;
        }

        private void setValue(int x, int y, double val) {
            data[x + y * 3] = val;
        }

        /**
         * Get an element from the matrix.
         * @param x The column of the element to get, assumed to be 0 to 2.
         * @param y The row of the element to get, assumed to be 0 to 2.
         * @return The value at x, y
         */
        public double getValue(int x, int y) {
            return data[x + y * 3];
        }

        /**
         * Prints out the contents of the matrix to the standard output.
         */
        public void debug() {
            System.out.println();
            System.out.println("[" + data[0] + ", " + data[1] + ", " + data[2]);
            System.out.println(data[3] + ", " + data[4] + ", " + data[5]);
            System.out.println(data[6] + ", " + data[7] + ", " + data[8] + "]");
        }

    }

    /**
     * A simple three dimensional rotation class used to convert angles.
     */
    public static class Rotation {
        private double rotationX;
        private double rotationY;
        private double rotationZ;

        private Rotation(double rotationX, double rotationY, double rotationZ) {
            this.rotationX = rotationX;
            this.rotationY = rotationY;
            this.rotationZ = rotationZ;
        }

        /**
         * Create a new rotation object from the given angles in radians
         * @param rotationX X rotation in radians
         * @param rotationY Y rotation in radians
         * @param rotationZ Z rotation in radians
         * @return A new rotation object
         */
        public static Rotation createFromRadians(double rotationX, double rotationY, double rotationZ) {
            return new Rotation(rotationX, rotationY, rotationZ);
        }

        /**
         * Create a new rotation object from the given angles in degrees
         * @param rotationX X rotation in degrees
         * @param rotationY Y rotation in degrees
         * @param rotationZ Z rotation in degrees
         * @return A new rotation object
         */
        public static Rotation createFromDegrees(double rotationX, double rotationY, double rotationZ) {
            return new Rotation(Math.toRadians(rotationX), Math.toRadians(rotationY), Math.toRadians(rotationZ));
        }


        /**
         * Get the X rotation in radians
         * @return X rotation in radians
         */
        public double getRadiansX() {
            return rotationX;
        }

        /**
         * Get the Y rotation in radians
         * @return Y rotation in radians
         */
        public double getRadiansY() {
            return rotationY;
        }

        /**
         * Get the Z rotation in radians
         * @return Z rotation in radians
         */
        public double getRadiansZ() {
            return rotationZ;
        }

        /**
         * Get the X rotation in degrees
         * @return X rotation in degrees
         */
        public double getDegreesX() {
            return Math.toDegrees(rotationX);
        }

        /**
         * Get the Y rotation in degrees
         * @return Y rotation in degrees
         */
        public double getDegreesY() {
            return Math.toDegrees(rotationY);
        }

        /**
         * Get the Z rotation in degrees
         * @return Z rotation in degrees
         */
        public double getDegreesZ() {
            return Math.toDegrees(rotationZ);
        }

        /**
         * Create a rotation object by factorizing a rotation matrix.
         *
         * The factorisation is done in the following order: Z, Y, Z
         *
         * Based on http://www.geometrictools.com/Documentation/EulerAngles.pdf
         * @param matrix The rotation matrix to factorise
         * @return A new rotation object
         */
        public static Rotation createFromMatrixZYX(Matrix3x3 matrix) {
            Rotation result = new Rotation(0, 0, 0);


            if (matrix.getValue(0, 2) < 1) {
                if (matrix.getValue(0, 2) > -1) {
                    result.rotationY = Math.asin(-matrix.getValue(0, 2));
                    result.rotationZ = Math.atan2(matrix.getValue(0, 1), matrix.getValue(0, 0));
                    result.rotationX = Math.atan2(matrix.getValue(1, 2), matrix.getValue(2, 2));

                } else {
                    result.rotationY = Math.PI / 2;
                    result.rotationZ = -Math.atan2(-matrix.getValue(2, 1), matrix.getValue(1, 1));
                    result.rotationX = 0;
                }
            } else {
                result.rotationY = -Math.PI /2;
                result.rotationZ = Math.atan2(-matrix.getValue(2, 1), matrix.getValue(1, 1));
                result.rotationX = 0;
            }

            return result;
        }

        /**
         * Create a rotation object by factorizing a rotation matrix.
         *
         * The factorisation is done in the following order: Y, Z, X
         *
         * Based on http://www.geometrictools.com/Documentation/EulerAngles.pdf
         * @param matrix The rotation matrix to factorise
         * @return A new rotation object
         */
        public static Rotation createFromMatrixYZX(Matrix3x3 matrix) {
            Rotation result = new Rotation(0, 0, 0);

            if (matrix.getValue(0, 1) < 1) {
                if (matrix.getValue(0, 1) > -1) {
                    result.rotationZ = Math.asin(matrix.getValue(0, 1));
                    result.rotationY = Math.atan2(-matrix.getValue(0, 2), matrix.getValue(0, 0));
                    result.rotationX = Math.atan2(-matrix.getValue(2, 1), matrix.getValue(1, 1));
                } else {
                    result.rotationZ = -Math.PI / 2;
                    result.rotationY = -Math.atan2(matrix.getValue(1, 2), matrix.getValue(2, 2));
                    result.rotationX = 0;
                }
            } else {
                result.rotationZ = Math.PI / 2;
                result.rotationY = Math.atan2(matrix.getValue(1, 2), matrix.getValue(2, 2));
                result.rotationX = 0;
            }

            return result;
        }

    }

    //Just an utility class, don't create an instance of it
    private TechneConverter() {}
}
