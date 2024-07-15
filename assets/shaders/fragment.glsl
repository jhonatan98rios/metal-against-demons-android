#ifdef GL_ES
precision mediump float;
#endif

varying vec2 v_texCoords;
uniform sampler2D u_texture;

void main() {
    vec4 color = texture2D(u_texture, v_texCoords);
    color.rgb = vec3(1.0) - color.rgb;
    gl_FragColor = color;
}
