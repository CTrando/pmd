attribute vec4 a_position;
attribute vec4 a_color;
attribute vec2 a_texCoord0;

uniform float time;
uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 vTexCoord0;

varying float thing;
void main() {
    thing = time;
    v_color = a_color;
    vTexCoord0 = a_texCoord0;
    gl_Position = u_projTrans * a_position;
}