// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   Matrix3D.java

package lattice.gui.graph.threeD;


class Matrix3D
{

    float xx;
    float xy;
    float xz;
    float xo;
    float yx;
    float yy;
    float yz;
    float yo;
    float zx;
    float zy;
    float zz;
    float zo;
    static final double pi = 3.1415926500000002D;

    Matrix3D()
    {
        xx = 1.0F;
        yy = 1.0F;
        zz = 1.0F;
    }

    void scale(float f)
    {
        xx *= f;
        xy *= f;
        xz *= f;
        xo *= f;
        yx *= f;
        yy *= f;
        yz *= f;
        yo *= f;
        zx *= f;
        zy *= f;
        zz *= f;
        zo *= f;
    }

    void scale(float xf, float yf, float zf)
    {
        xx *= xf;
        xy *= xf;
        xz *= xf;
        xo *= xf;
        yx *= yf;
        yy *= yf;
        yz *= yf;
        yo *= yf;
        zx *= zf;
        zy *= zf;
        zz *= zf;
        zo *= zf;
    }

    void translate(float x, float y, float z)
    {
        xo += x;
        yo += y;
        zo += z;
    }

    void yrot(double theta)
    {
        theta *= 0.017453292500000002D;
        double ct = Math.cos(theta);
        double st = Math.sin(theta);
        float Nxx = (float)((double)xx * ct + (double)zx * st);
        float Nxy = (float)((double)xy * ct + (double)zy * st);
        float Nxz = (float)((double)xz * ct + (double)zz * st);
        float Nxo = (float)((double)xo * ct + (double)zo * st);
        float Nzx = (float)((double)zx * ct - (double)xx * st);
        float Nzy = (float)((double)zy * ct - (double)xy * st);
        float Nzz = (float)((double)zz * ct - (double)xz * st);
        float Nzo = (float)((double)zo * ct - (double)xo * st);
        xo = Nxo;
        xx = Nxx;
        xy = Nxy;
        xz = Nxz;
        zo = Nzo;
        zx = Nzx;
        zy = Nzy;
        zz = Nzz;
    }

    void xrot(double theta)
    {
        theta *= 0.017453292500000002D;
        double ct = Math.cos(theta);
        double st = Math.sin(theta);
        float Nyx = (float)((double)yx * ct + (double)zx * st);
        float Nyy = (float)((double)yy * ct + (double)zy * st);
        float Nyz = (float)((double)yz * ct + (double)zz * st);
        float Nyo = (float)((double)yo * ct + (double)zo * st);
        float Nzx = (float)((double)zx * ct - (double)yx * st);
        float Nzy = (float)((double)zy * ct - (double)yy * st);
        float Nzz = (float)((double)zz * ct - (double)yz * st);
        float Nzo = (float)((double)zo * ct - (double)yo * st);
        yo = Nyo;
        yx = Nyx;
        yy = Nyy;
        yz = Nyz;
        zo = Nzo;
        zx = Nzx;
        zy = Nzy;
        zz = Nzz;
    }

    void zrot(double theta)
    {
        theta *= 0.017453292500000002D;
        double ct = Math.cos(theta);
        double st = Math.sin(theta);
        float Nyx = (float)((double)yx * ct + (double)xx * st);
        float Nyy = (float)((double)yy * ct + (double)xy * st);
        float Nyz = (float)((double)yz * ct + (double)xz * st);
        float Nyo = (float)((double)yo * ct + (double)xo * st);
        float Nxx = (float)((double)xx * ct - (double)yx * st);
        float Nxy = (float)((double)xy * ct - (double)yy * st);
        float Nxz = (float)((double)xz * ct - (double)yz * st);
        float Nxo = (float)((double)xo * ct - (double)yo * st);
        yo = Nyo;
        yx = Nyx;
        yy = Nyy;
        yz = Nyz;
        xo = Nxo;
        xx = Nxx;
        xy = Nxy;
        xz = Nxz;
    }

    void mult(Matrix3D rhs)
    {
        float lxx = xx * rhs.xx + yx * rhs.xy + zx * rhs.xz;
        float lxy = xy * rhs.xx + yy * rhs.xy + zy * rhs.xz;
        float lxz = xz * rhs.xx + yz * rhs.xy + zz * rhs.xz;
        float lxo = xo * rhs.xx + yo * rhs.xy + zo * rhs.xz + rhs.xo;
        float lyx = xx * rhs.yx + yx * rhs.yy + zx * rhs.yz;
        float lyy = xy * rhs.yx + yy * rhs.yy + zy * rhs.yz;
        float lyz = xz * rhs.yx + yz * rhs.yy + zz * rhs.yz;
        float lyo = xo * rhs.yx + yo * rhs.yy + zo * rhs.yz + rhs.yo;
        float lzx = xx * rhs.zx + yx * rhs.zy + zx * rhs.zz;
        float lzy = xy * rhs.zx + yy * rhs.zy + zy * rhs.zz;
        float lzz = xz * rhs.zx + yz * rhs.zy + zz * rhs.zz;
        float lzo = xo * rhs.zx + yo * rhs.zy + zo * rhs.zz + rhs.zo;
        xx = lxx;
        xy = lxy;
        xz = lxz;
        xo = lxo;
        yx = lyx;
        yy = lyy;
        yz = lyz;
        yo = lyo;
        zx = lzx;
        zy = lzy;
        zz = lzz;
        zo = lzo;
    }

    void unit()
    {
        xo = 0.0F;
        xx = 1.0F;
        xy = 0.0F;
        xz = 0.0F;
        yo = 0.0F;
        yx = 0.0F;
        yy = 1.0F;
        yz = 0.0F;
        zo = 0.0F;
        zx = 0.0F;
        zy = 0.0F;
        zz = 1.0F;
    }

    void transform(float v[], int tv[], int nvert)
    {
        float lxx = xx;
        float lxy = xy;
        float lxz = xz;
        float lxo = xo;
        float lyx = yx;
        float lyy = yy;
        float lyz = yz;
        float lyo = yo;
        float lzx = zx;
        float lzy = zy;
        float lzz = zz;
        float lzo = zo;
        for(int i = nvert * 3; (i -= 3) >= 0;)
        {
            float x = v[i];
            float y = v[i + 1];
            float z = v[i + 2];
            tv[i] = (int)(x * lxx + y * lxy + z * lxz + lxo);
            tv[i + 1] = (int)(x * lyx + y * lyy + z * lyz + lyo);
            tv[i + 2] = (int)(x * lzx + y * lzy + z * lzz + lzo);
        }

    }

    public String toString()
    {
        return "[" + xo + "," + xx + "," + xy + "," + xz + ";" + yo + "," + yx + "," + yy + "," + yz + ";" + zo + "," + zx + "," + zy + "," + zz + "]";
    }
}
